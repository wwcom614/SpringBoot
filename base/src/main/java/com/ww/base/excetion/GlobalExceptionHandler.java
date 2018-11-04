package com.ww.base.excetion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
//作为一个Controller层切面处理，用于拦截全局的Controller的异常。注意：ControllerAdvice注解只拦截Controller的异常。
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error/500";
    //获取所有异常
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e){//出现异常后由该方法处理
        ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW); //设置跳转路径
        mav.addObject("url", request.getRequestURL());
        log.error("【Exception】：{}", e.getMessage());
        return mav;
    }
}
