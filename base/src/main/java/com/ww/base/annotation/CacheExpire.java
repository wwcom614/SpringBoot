package com.ww.base.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

//自定义注解，可以在缓存方法上添加过期时间
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheExpire {
    /**
     * expire time, default 60s
     */
    @AliasFor("expire")
    long value() default 60L;

    /**
     * expire time, default 60s
     */
    @AliasFor("value")
    long expire() default 60L;

}
