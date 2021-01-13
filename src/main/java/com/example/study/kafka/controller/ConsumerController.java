package com.example.study.kafka.controller;

import com.example.study.kafka.model.Event;
import com.example.study.kafka.service.ConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @PostMapping("/consumer")
    public void createConsumer(@RequestBody Event event) throws ExecutionException, InterruptedException, JsonProcessingException {
        consumerService.consumer(event.toString());
    }


}
