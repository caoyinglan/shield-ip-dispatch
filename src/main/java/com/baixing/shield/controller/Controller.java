package com.baixing.shield.controller;

import com.baixing.ms.springtime.modules.api.R;
import com.baixing.shield.service.kafka.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/shield-ip-dispatch")
public class Controller {

    private final Producer producer;

    @PostMapping("produce")
    public R produce(){
        producer.send();
        return R.status(true);
    }
}
