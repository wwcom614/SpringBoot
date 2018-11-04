package com.ww.base.controller;

import com.ww.base.service.InfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class GetBeanConfigController {

    //要与Bean配置类中的@Bean(name = "XX") 名称完全一样。
    //@Resource(name="ConfigClassBeanService")

    //要与spring-common.xml中的bean id = XX 名称完全一样
    @Resource(name="xmlBeanService")
    private InfoService infoService;

    //获取service的bean配置：http://localhost:80/bean-config
    @GetMapping("/bean-config")
    public String getBeanCongfig() {
        return this.infoService.info();
    }


}
