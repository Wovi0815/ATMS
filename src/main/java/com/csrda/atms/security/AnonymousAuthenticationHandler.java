 
package com.csrda.atms.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.csrda.atms.utils.Result;
import com.csrda.atms.utils.ResultCode;

/**  
* @author WangWei
* @description 匿名用户访问资源处理器
* @Date 2022年9月3日 下午10:28:46
*/
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        
        // 设置客户端响应编码格式
        response.setContentType("application/json;charset=UTF-8");
        //获取输出流   
        ServletOutputStream outputStream =response.getOutputStream();
        String result = JSON.toJSONString(Result.error().code(ResultCode.NO_LOGIN).message("匿名用户无权访问!"),
            SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        
    }

}
   