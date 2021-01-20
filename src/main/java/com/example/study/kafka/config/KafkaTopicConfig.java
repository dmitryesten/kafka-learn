package com.example.study.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    @Primary
    public NewTopic springObjectTopic(){
        return TopicBuilder.name("spring-object-topic")
                .partitions(2)
                .build();
    }

    @Bean
    public NewTopic springStringTopic(){
        return TopicBuilder.name("spring-string-topic")
                .partitions(1)
                .compact()
                .build();
    }

}
