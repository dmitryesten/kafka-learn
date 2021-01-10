package com.example.study.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "topic-0", groupId = "group_id")
    public void consumer(String message){
        System.out.println("Consumed message: " + message);
    }

}
