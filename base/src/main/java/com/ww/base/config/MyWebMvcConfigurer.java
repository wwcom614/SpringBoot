package com.ww.base.config;

import com.ww.base.util.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//基础拦截器
@Configuration
//在SpringBoot2.0及Spring 5.0 WebMvcConfigurerAdapter已被废弃，官方推荐直接实现WebMvcConfigurer接口
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor注册自己自定义的拦截器，addPathPatterns指定URL拦截
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/1");
    }
}
