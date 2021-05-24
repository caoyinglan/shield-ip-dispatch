package com.baixing.shield;

import com.baixing.ms.springtime.modules.EnableSpringTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: ao.wang
 *
 * @create: 2021-05-24
 **/
@SpringBootApplication
@EnableSpringTime
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
