package com.ww.base.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class KafkaConsumerServiceImpl {
    @KafkaListener(topics = "ww")
    public void receiveMessage(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
        //判断接收记录是否NULL--JDK8新特性：使用Optional避免null导致的NullPointerException
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        //使用Optional.isPresent()：判断值是否存在
        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get();

            log.info("【Receiver】 Topic:" + topic);
            log.info("【Receiver】 Partition:" + record.partition());
            log.info("【Receiver】 Headers:" + record.headers());
            log.info("【Receiver】 Offset:" + record.offset());
            log.info("【Receiver】 Message:" + message);
            log.info("【Receiver】 Record:" + record);
        }
    }
}
