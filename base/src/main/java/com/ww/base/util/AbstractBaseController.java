package com.ww.base.util;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Locale;

//资源文件读取抽象类，统一封装资源对象MessageSource
public abstract class AbstractBaseController {

    @Resource
    private MessageSource messageSource; //装载资源对象

    public String getMessage(String key, String ... args){
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

    //当提交表单时，controller会把表单元素注入到command类里，
    // 但是系统注入的只能是基本类型，如int，char，String。
    // 但当我们在command类里需要复杂类型，如Integer，date，或自己定义的类时，
    // controller就不会那么聪明了。这时，就需要我们帮助他们了。
    // 可以使用注解@InitBinder来解决这些问题，
    // 这样SpingMVC在绑定表单之前，都会先注册这些编辑器。
    // 一般会将这些方法些在BaseController中，需要进行这类转换的控制器只需继承BaseController即可。
    // Spring提供了很多的实现类，如CustomDateEditor、CustomBooleanEditor、CustomNumberEditor等，基本上是够用的
    @InitBinder
    public void initBinder(WebDataBinder binder){
        //将页面请求过来的字符串格式日期，转换为java.util.Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(simpleDateFormat,true));

    }
}
