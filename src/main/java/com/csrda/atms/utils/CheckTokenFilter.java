 
package com.csrda.atms.utils;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.csrda.atms.security.CustomerAuthenticationException;
import com.csrda.atms.security.CustomerUserDetailService;
import com.csrda.atms.security.LoginFailureHandler;

import lombok.Data;

/**  
* @author WangWei
* @description
* @Date 2022年9月24日 下午9:27:43
*/
@Data
@Component
public class CheckTokenFilter extends OncePerRequestFilter{
       
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private CustomerUserDetailService customerUserDetailService;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private RedisService redisService;
    
    //获取登录地址
    @Value ("${request.login.url}")
    private String loginUrl;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        try{
            //获取当前请求地址
            String url = request.getRequestURI();

            //如果当前请求不是登录请求，则需要进行token验证
            if(!url.equals(loginUrl)) {
                this.validateToken(request);
            }
        }catch(AuthenticationException e) {
            loginFailureHandler.onAuthenticationFailure(request, response, e);
        }
        //不需要携带token，直接访问
        doFilter(request, response, filterChain);
    }
    /**
     * 验证token
     * @param request
     */
    private void validateToken(HttpServletRequest request) throws AuthenticationException{
        
        
        //从头部获取token信息
        String token = request.getHeader("token");
        //如果头部没有获取到token，则从请求的参数中获取
        if(ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        //如果请求参数中也不存在token信息，则抛出异常
        if(ObjectUtils.isEmpty(token)) {
            throw new CustomerAuthenticationException("token不存在");
        }
        

        
        //判断redis中是否存在该token
        String tokenKey = "token_"+token;
        String redisToken= redisService.get(tokenKey);
        //如果redis里没有token，说明token失效
        if(ObjectUtils.isEmpty(redisToken)) {
            throw new CustomerAuthenticationException("token已失效");
        }
        //如果token和redis里的token不一样，则验证失败
        if(!token.equals(redisToken)) {
            throw new CustomerAuthenticationException("token验证失败");
        }
        //如果存在token，从中解析出用户名
        String username = jwtUtils.getUsernameFromToken(token);
        //如果用户名为空，则解析失败
        if(ObjectUtils.isEmpty(username)) {
            throw new CustomerAuthenticationException("token解析失败");
        }
        
        //获取用户信息
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(username);
        //判断用户信息是否为空
        if(userDetails == null) {
            throw new CustomerAuthenticationException("token验证失败"); 
        }
        
        //创建身份验证对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,null,userDetails.getAuthorities());
       
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        
        //设置到spring security上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
   