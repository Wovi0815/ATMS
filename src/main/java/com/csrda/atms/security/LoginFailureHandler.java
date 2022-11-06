 
package com.csrda.atms.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.csrda.atms.utils.Result;

/**  
* @author WangWei
* @description 自定义认证失败处理器
* @Date 2022年9月3日 下午7:58:26
*/
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
  
        // 设置客户端响应编码格式
        response.setContentType("application/json;charset=UTF-8");
        //获取输出流   
        ServletOutputStream outputStream =response.getOutputStream();
        String message = null;//提示信息
        int code =500;
        //判断异常类型
        if(exception instanceof AccountExpiredException) {
            message = "账户过期,登录失败!";
        }else if(exception instanceof BadCredentialsException) {
            message = "用户名或密码错误,登录失败!";
        }else if(exception instanceof CredentialsExpiredException) {
            message = "密码过期,登录失败!";
        }else if(exception instanceof DisabledException) {
            message = "账户被禁用,登录失败!";
        }else if(exception instanceof LockedException) {
            message = "账户被锁,登录失败!";
        }else if(exception instanceof InternalAuthenticationServiceException) {
            message = "账户不存在,登录失败!";
        }else if(exception instanceof CustomerAuthenticationException) {
            message =exception.getMessage();
            code = 401;
      }else {
            message = "登录失败!";
        }
        
        //将错误信息转换成JSON  
        String result = JSON.toJSONString(Result.error().code(code).message(message));
  
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
   