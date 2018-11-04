package com.ww.base.service.impl;

import com.ww.base.model.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaSendReceiveTest {

    @Resource
    private KafkaProducerServiceImpl<KafkaMessage> kafkaSendMsg;

    @Test
    public void sendMessage() throws Exception {
        for(int i=0; i<10; i++){
            KafkaMessage msg = new KafkaMessage();
            msg.setId(System.currentTimeMillis());
            msg.setMsg(String.valueOf(UUID.randomUUID()));
            msg.setSendTime(new Date());

            kafkaSendMsg.sendMessage(msg);
            Thread.sleep(1000);
        }

    }

}