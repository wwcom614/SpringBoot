package com.ww.base.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//AOP拦截器
@Aspect
@Component
@Slf4j
public class ServiceAspect {

    @Around("execution(* com.ww..service.InfoService.*(..))")
    public Object arroundInvoke(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("【Service-Before】执行参数：{}", Arrays.toString(joinPoint.getArgs()));
        Object obj = joinPoint.proceed(joinPoint.getArgs());
        log.info("【Service-After】返回结果：{}", obj);
        return obj;
    }
}
