package com.ww.base.controller;

import com.ww.base.config.GetApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class GetApplicationConfigController {

    //@Value注解方式从application.yml获取配置：http://localhost:80/person/annotation-value
    @Value("${self-config.person.name}")
    private String personName;

    @Value("${self-config.person.age}")
    private String personAge;

    @GetMapping("/annotation-value")
    public String getConfigByValue(){
        return new StringBuilder().append("personName：").append(personName).
                append("，personAge：").append(personAge).toString();
    }




    //Config类方式从application.yml获取配置：http://localhost:80/person/config-class
    @Autowired
    private GetApplicationConfig personConfig;

    @GetMapping("/config-class")
    public String getConfigByConfigClass(){
        return new StringBuilder().append("personName：").append(personConfig.getName()).
                append("，personAge：").append(personConfig.getAge()).toString();
    }



}
