package com.ww.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "self-config.person")
//默认值不加该注解就是application.yml，可在此处指定自定义配置文件路径，例如i18n资源文件中的配置
//@PropertySource("classpath://application.yml")
public class GetApplicationConfig {

    private String name;

    private Integer age;

}
