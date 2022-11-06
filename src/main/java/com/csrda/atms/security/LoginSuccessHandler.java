 
package com.csrda.atms.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.csrda.atms.pojo.User;
import com.csrda.atms.utils.JwtUtils;
import com.csrda.atms.utils.LoginResult;
import com.csrda.atms.utils.RedisService;
import com.csrda.atms.utils.ResultCode;

import io.jsonwebtoken.Jwts;

/**  
* @author WangWei
* @description  自定义认证成功处理器
* @Date 2022年9月3日 下午7:32:21
*/
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
   @Resource
   private JwtUtils jwtUtils;
   @Resource
   private RedisService redisService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        // 设置客户端响应的内容类型
        response.setContentType("application/json;charset=UTF-8");
        //获取登录用户信息
        User user =(User)authentication.getPrincipal();
        //生成token
        String token =jwtUtils.generateToken(user);
        //设置token签名密钥及过期时间
        long expireTime = Jwts.parser()//获取默认对象
            .setSigningKey(jwtUtils.getSecret())//设置签名密钥
            .parseClaimsJws(token.replace("jwt_", ""))
            .getBody().getExpiration().getTime();//获取过期时间
        
        //创建登录结果对象
        LoginResult loginResult = new LoginResult(user.getId(),ResultCode.SUCCESS,token,expireTime);  
        String result = JSON.toJSONString(loginResult,SerializerFeature.DisableCircularReferenceDetect);
        //获取输出流   
        ServletOutputStream outputStream =response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        //把生成的token存到redis
        String tokenKey ="token_"+token;
        System.out.println("tokenKey:"+tokenKey);
        redisService.set(tokenKey, token, jwtUtils.getExpiration()/1000);
        
    }

}
   