package com.ww.base.model;

import lombok.Data;

import java.util.Date;

@Data
public class KafkaMessage {
    private Long id;
    private String msg;
    private Date sendTime;

}
