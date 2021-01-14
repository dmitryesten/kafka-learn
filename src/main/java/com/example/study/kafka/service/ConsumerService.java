package com.example.study.kafka.service;

import com.example.study.kafka.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Slf4j
@KafkaListener(topics = "spring-topic-test", groupId = "group_id")
@Service
public class ConsumerService {

    @Autowired
    private RedisTemplate<String, Event> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaHandler
    public void consumer(Event event){
        log.info("Consumed event object in kafka "  + event.toString());
        redisTemplate.opsForHash().put(event.getKey(), event.hashCode(), event);
        log.info("Consumed message: " + redisTemplate.opsForHash().get(event.getKey(), event.hashCode()));
    }

}
