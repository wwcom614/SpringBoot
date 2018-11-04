package com.ww.base.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

@Slf4j
@Service
public class KafkaProducerServiceImpl<T>{
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(T obj){
        //使用FastJson将对象转换为String
        String jsonStringMessage = JSON.toJSONString(obj);

        //发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("ww", jsonStringMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(@Nullable SendResult<String, String> stringStringSendResult) {
                //TODO 业务处理
                log.info("【Producer】 The message was sent successfully:");
                log.info("【Producer】 result: " + stringStringSendResult.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Produce: The message failed to be sent:" + ex.getMessage());
            }
        });
    }

}
