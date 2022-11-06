 
package com.csrda.atms.utils;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**  
* @author WangWei
* @description
* @Date 2022年8月20日 下午11:23:50
*/

public class CORSConfig implements WebMvcConfigurer{
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //允许所有访问请求
                .allowedMethods("*")//允许所有访问请求方法访问该跨域资源
                .allowedOrigins("*")//允许所有访问请求方法访问我们的跨域资源
                .allowedHeaders("*");//允许所有请求header访问
    }

}
   