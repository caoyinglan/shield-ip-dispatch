package com.baixing.shield.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long sendTime;
    private Long expiredTime;
    private String type;
    private String ip;
    private Double score;
    private String service;
}
