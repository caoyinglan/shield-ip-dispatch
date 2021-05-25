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

    private final KafkaTemplate kafkaTemplate;

    public void send() {
        Message message1 = buildMessage("1.2.3", 80, "delete", "dianpu");
        Message message2 = buildMessage("1.2.4", 70, "auto", "zhidao");
        Message message3 = buildMessage("1.2.5", 40, "manual", "zhidao");
        Message message4 = buildMessage("1.2.6", 20, "auto", "dianpu");
        List<Message> messages = List.of(message1,message2,message3,message4);
        messages.forEach(message -> {String messageJson = JsonMapper.INSTANCE.toJson(message);
            kafkaTemplate.send(CONSUMER_TOPIC,messageJson);
        });
    }

    public Message buildMessage(String ip, Integer score, String type, String service) {
        Message message = new Message();
        message.setSendTime(new Date());
        message.setExpiredTime(3600L);
        message.setIp(ip);
        message.setScore(score);
        message.setType(type);
        message.setService(service);
        return message;
    }
}
