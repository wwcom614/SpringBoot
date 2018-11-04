package com.ww.base.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class MyScheduler {

    @Scheduled(fixedRate = 10000) //间隔调度，fixedRate秒一次
    public void intervalJob(){
        try{
            Thread.sleep(5000);// Thread.sleep和fixedRate，哪个大以哪个为准定制调度。
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        log.info("【间隔调度】：{}", new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }

    //springboot不支持年的cron定时任务，固定6位。cron使用见网址 http://cron.qqe2.com/
    @Scheduled(cron = "* * * * * ?") //cron调度，1秒一次
    public void cronJob(){
        log.warn("【Cron调度】：{}", new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }
}
