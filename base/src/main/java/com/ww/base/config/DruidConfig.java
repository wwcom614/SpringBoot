package com.ww.base.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DruidConfig {
    @Bean
    //从application.yml中读取spring.datasource下配置的数据源，作为一个配置Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Bean
    //配置后台访问鉴权信息
    public ServletRegistrationBean druidSevlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new StatViewServlet(),"/druid/*");
        //设置允许查看Druid监控的白名单IP
        servletRegistrationBean.addInitParameter("allow","127.0.0.1,192.168.1.1");
        //设置禁止查看Druid监控的黑名单IP
        servletRegistrationBean.addInitParameter("deny","192.168.1.253");
        servletRegistrationBean.addInitParameter("loginUserName","ww");
        servletRegistrationBean.addInitParameter("loginPassword","ww");
        //是否可以重置数据源
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    @Bean
    //配置前台访问哪些做监控，哪些不做监控
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        //对所有请求监控
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "/druid/*,*.js,*.css,*.bmp,*.jpg,*,jpeg,*,gif");
        return filterRegistrationBean;


    }
}
