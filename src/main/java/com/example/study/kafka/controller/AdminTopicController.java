package com.example.study.kafka.controller;

import com.example.study.kafka.model.KafkaTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminTopicController {

    @Autowired
    public AdminClient adminClient;

    @GetMapping("/topics")
    public Set<String> getTopics() throws ExecutionException, InterruptedException {
        return adminClient.listTopics().names().get();
    }

    @GetMapping("/topics/{nameTopic}")
    public String getTopics(@PathVariable(name = "nameTopic") String nameTopic) throws ExecutionException, InterruptedException {
        return adminClient.listTopics().names().get().stream().filter(s -> s.equals(nameTopic)).findFirst().get();
    }

    @PostMapping("/create/topic")
    public Set<String> create(@RequestBody KafkaTopic kafkaTopic){
        return adminClient.createTopics(
                Collections.singleton(new NewTopic(kafkaTopic.getName(),
                        kafkaTopic.getPartition(), kafkaTopic.getReplication()))).values().keySet();
    }

    @PostMapping("/create/topics")
    public Set<String> create(@RequestBody List<KafkaTopic> kafkaTopics) {
        return adminClient.createTopics(
                kafkaTopics.stream()
                .map(s -> new NewTopic(s.getName(), s.getPartition(), s.getReplication()))
                .collect(Collectors.toCollection(HashSet::new))
        ).values().keySet();
    }

    @DeleteMapping("/delete/topic/{nameTopic}")
    public void delete(@PathVariable(name = "nameTopic") String nameTopic ){
        log.info("Deleting topic: " + nameTopic);
        adminClient.deleteTopics(Arrays.asList(nameTopic));
    }

}
