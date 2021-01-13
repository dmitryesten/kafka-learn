package com.example.study.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "spring-topic-test", groupId = "group_id")
    public void consumer(String message){
        redisTemplate.opsForHash().put("spring-session", message.hashCode(), message);
        log.info("Consumed message: " + redisTemplate.opsForHash().get("spring-session", message.hashCode()));
    }

}
