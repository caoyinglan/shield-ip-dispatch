package com.baixing.shield.service.kafka;


import com.baixing.ms.springtime.modules.utils.mapper.JsonMapper;
import com.baixing.shield.dispatch.DispatchService;
import com.baixing.shield.entity.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.baixing.shield.enums.Constants.CONSUMER_GROUP_ID;
import static com.baixing.shield.enums.Constants.CONSUMER_TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {

    private final DispatchService dispatchService;

    @KafkaListener(topics = CONSUMER_TOPIC,groupId = CONSUMER_GROUP_ID)
    public void listen1(String in) {
        log.info("receive message: {}", in);
        Message message = JsonMapper.INSTANCE.fromJson(in, Message.class);
        dispatchService.dispatch(message);
    }
}

