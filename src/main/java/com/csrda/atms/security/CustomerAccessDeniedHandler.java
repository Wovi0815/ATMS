 
package com.csrda.atms.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.csrda.atms.utils.Result;
import com.csrda.atms.utils.ResultCode;

/**  
* @author WangWei
* @description  认证用户无权限访问处理器
* @Date 2022年9月3日 下午10:47:09
*/
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置客户端响应编码格式
        response.setContentType("application/json;charset=UTF-8");
        //获取输出流   
        ServletOutputStream outputStream =response.getOutputStream();
        String result = JSON.toJSONString(Result.error().code(ResultCode.NO_AUTH).message("无权限访问,请联系管理员!"),
            SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
   