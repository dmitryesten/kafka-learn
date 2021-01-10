package com.example.study.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private String topic;

    @PostMapping("/producer")
    public String createEvent(@RequestBody String messsage) throws ExecutionException, InterruptedException {
        return "event is created: " +  kafkaTemplate.send(topic,messsage).get().toString();
    }

}
