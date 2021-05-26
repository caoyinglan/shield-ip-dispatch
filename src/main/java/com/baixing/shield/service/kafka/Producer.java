package com.baixing.shield.service.kafka;

import com.baixing.ms.springtime.modules.utils.mapper.JsonMapper;
import com.baixing.shield.entity.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.baixing.shield.enums.Constants.CONSUMER_TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public void send(String messageJson) {

        kafkaTemplate.send(CONSUMER_TOPIC,messageJson);

    }

    public Message buildMessage(String ip, Double score, String type, String service) {
        Message message = new Message();
        message.setSendTime(System.currentTimeMillis());
        message.setExpiredTime(3600L);
        message.setIp(ip);
        message.setScore(score);
        message.setType(type);
        message.setService(service);
        return message;
    }
}
