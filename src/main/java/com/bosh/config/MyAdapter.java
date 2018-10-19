/**
 * 
 */
package com.bosh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bosh.interceptor.LoginInterceptor;

/**
 * @author wangmt
 * @date 2018年10月18日
 */
@Configuration
public class MyAdapter implements WebMvcConfigurer {
	
	  
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    	//图片地址映射
        registry.addResourceHandler("/images/upload/**").addResourceLocations("file:D:/eclipse-workspace/images/upload/");
    }
    
    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

/*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns(
                "/posts/add",
                "/user/set",
                "/user/seticon",
                "/user/setpsw",
                "/user/message",
                "/chat");
        super.addInterceptors(registry);
    }
*/

}
