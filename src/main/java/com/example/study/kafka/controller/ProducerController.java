package com.example.study.kafka.controller;

import com.example.study.kafka.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class ProducerController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NewTopic springObjectTopic;

    @Autowired
    private NewTopic springStringTopic;

    @Autowired @Qualifier("kafkaStringTemplate")
    private KafkaTemplate<String, String> kafkaStringTemplate;

    @Autowired @Qualifier("kafkaObjectTemplate")
    private KafkaTemplate<String, Event> kafkaTemplate;

    @PostMapping("/producer")
    public String createEvent(@RequestBody Event event) throws ExecutionException, InterruptedException, JsonProcessingException {
        log.info("INFO Event: " + event.toString());

        log.info("Send object to "  + springObjectTopic.name() + " topic");
        return kafkaTemplate.send(springObjectTopic.name(), event).get().toString();
    }

    @PostMapping("/producer/record")
    public String createEventRecord(@RequestBody Event event) throws ExecutionException, InterruptedException {
        log.info("INFO Post send event wrapped record: " + event.toString());
        ProducerRecord<String, Event> producerRecordEvent =
                new ProducerRecord(springObjectTopic.name(), event.getKey(), event.getValue());

        return kafkaTemplate.send(producerRecordEvent).get().toString();
    }

}
