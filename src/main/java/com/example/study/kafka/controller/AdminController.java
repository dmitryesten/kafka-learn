package com.example.study.kafka.controller;

import com.example.study.kafka.model.KafkaTopic;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

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
        adminClient.deleteTopics(Collections.singleton(nameTopic));
    }

}
