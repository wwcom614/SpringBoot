package com.ww.base.controller;

import com.ww.base.util.AbstractBaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class Geti18nConfigController extends AbstractBaseController {

    //获取i18n资源文件配置：http://localhost:80/person/i18n?name=ww&&age=38
    @GetMapping("/i18n")
    public String readi18n(@RequestParam(value="name") String name,
                           @RequestParam(value="age",required = false,defaultValue = "20") Integer age){
       return new StringBuilder().append(super.getMessage("person.name", name)).append("，").
               append(super.getMessage("person.age", String.valueOf(age))).toString();
    }
}
