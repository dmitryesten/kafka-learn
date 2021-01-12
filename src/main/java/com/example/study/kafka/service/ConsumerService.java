package com.example.study.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @KafkaListener(topics = "topic-0", groupId = "group_id")
    public void consumer(String message){
        log.info("Consumed message: " + message);

    }

}
