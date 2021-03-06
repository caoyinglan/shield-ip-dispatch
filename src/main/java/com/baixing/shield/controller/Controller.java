package com.baixing.shield.controller;

import com.baixing.ms.springtime.modules.api.R;
import com.baixing.shield.entity.dto.Message;
import com.baixing.shield.service.kafka.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/shield-ip-dispatch")
public class Controller {

    private final Producer producer;

    @PostMapping("produce")
    public String produce(@RequestBody String messageJson){
        producer.send(messageJson);
        return "success";
    }
}
