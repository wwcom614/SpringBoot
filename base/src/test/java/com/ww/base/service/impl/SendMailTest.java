package com.ww.base.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMailTest {

    @Resource
    private JavaMailSender javaMailSender;

    @Test
    public void sendMail() throws Exception {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("wwcom614@qq.com");
        simpleMailMessage.setTo("wang.wei32@zte.com.cn");
        simpleMailMessage.setSubject("你好");
        simpleMailMessage.setText("好好学习，天天向上！");
        javaMailSender.send(simpleMailMessage);
    }
}