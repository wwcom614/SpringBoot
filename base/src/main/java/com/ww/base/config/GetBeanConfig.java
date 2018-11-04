package com.ww.base.config;

import com.ww.base.service.InfoService;
import com.ww.base.service.impl.InfoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //表明此处为配置项
public class GetBeanConfig {
    @Bean(name = "ConfigClassBeanService")  //返回一个Spring的配置Bean，与xml方式的 <bean>等价
    public InfoService infoService(){
        return new InfoServiceImpl();
    }
}
