package com.ww.base.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
//自定义拦截器可用于输入验证和错误提示
public class MyInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        log.info("【MyInterceptor-preHandle】: {}", handlerMethod.getBean().getClass().getSimpleName());
        //返回false则拦截(不继续进行)；返回true则继续进行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        log.info("【MyInterceptor-postHandle】: {}", handlerMethod.getBean().getClass().getSimpleName());
        log.info("【MyInterceptor-postHandle】: {}",modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("【MyInterceptor-afterCompletion】: 拦截器处理完毕！");
    }
}
