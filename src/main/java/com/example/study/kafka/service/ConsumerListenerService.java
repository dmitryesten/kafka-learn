package com.example.study.kafka.service;

import com.example.study.kafka.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ConsumerListenerService {

    @Autowired
    private RedisTemplate<String, Event> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "spring-object-topic", containerFactory = "kafkaListenerObjectContainerFactory")
    public void consumer(Event event) {
        log.info("Consumer event object of kafka "  + event.toString());
        redisTemplate.opsForHash().put(event.getKey(), event.getKey().hashCode(), event.toString());
    }

    @KafkaListener(topics = "spring-string-topic", containerFactory = "kafkaListenerStringContainerFactory")
    public void consumerString(String eventString) throws JsonProcessingException {
        log.info("Consumer event string of kafka "  + eventString);
        Event event = objectMapper.readValue(eventString, Event.class);
        log.info("Convert string to object event"  + event.toString());
        redisTemplate.opsForHash().put(event.getKey(), event.getKey().hashCode(), event.toString());
    }

}
