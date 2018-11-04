package com.ww.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication  //启动SpringBoot程序，然后子包扫描
@ImportResource(locations = {"classpath:spring-common.xml"})
@MapperScan("com.ww.base.mapper") //配置mybatis的mapper接口扫描
@EnableTransactionManagement  //启用事务控制总开关
//@EnableScheduling //支持定时任务调度的总开关
@EnableCaching
public class BaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
}
