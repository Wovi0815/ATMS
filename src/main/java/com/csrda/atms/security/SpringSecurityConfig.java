 
package com.csrda.atms.security;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.csrda.atms.utils.CheckTokenFilter;





/**  
* @author WangWei
* @description
* @Date 2022年9月3日 下午10:58:26
*/



@Configuration
@EnableWebSecurity

public class SpringSecurityConfig  {
    @Resource
    private CustomerUserDetailService customerUserDetailService;
    
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    
    @Resource
    private LoginFailureHandler loginFailureHandler;
    
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;
    
    
    @Resource
    private CheckTokenFilter checkTokenFilter;
    
    //注入加密处理类
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //处理登录
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        
     //登录前进行过滤
       http.addFilterBefore(checkTokenFilter,UsernamePasswordAuthenticationFilter.class);
      
        //登录过程处理
        http.formLogin()
                .loginProcessingUrl("/api/user/login")
                .successHandler(loginSuccessHandler)//认证成功处理器
                .failureHandler(loginFailureHandler)//认证失败处理器
                .usernameParameter("username")
                .passwordParameter("password")
                //禁用csrf防御机制
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//不创建session
                .and()
                .authorizeHttpRequests()//设置需要拦截的请求
                .antMatchers("/api/user/login").permitAll()//登录请求放行
                .anyRequest().authenticated()//其他的都需要身份验证
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationHandler)//匿名（未认证）无权限
                .accessDeniedHandler(customerAccessDeniedHandler)//认证用户无权限
                .and().cors();//开启跨域配置
 
       return http.build();
    
    }
    
    
    
    
    
    
    
    //配置认证处理器
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(customerUserDetailService).passwordEncoder(passwordEncoder());
    }
    
    protected void configure(HttpSecurity http) throws Exception{
      //登录前进行过滤
        http.addFilterBefore(checkTokenFilter,UsernamePasswordAuthenticationFilter.class);
        http.formLogin();
}
    
}
   