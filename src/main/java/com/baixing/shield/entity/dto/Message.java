package com.baixing.shield.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Date sendTime;
    private Long expiredTime;
    private String type;
    private String ip;
    private Integer score;
    private String service;
}
