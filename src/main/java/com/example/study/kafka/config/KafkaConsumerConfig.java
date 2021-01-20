package com.example.study.kafka.config;

import com.example.study.kafka.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${remote.host}")
    private String host;

    @Bean
    public Map<String, Object> consumerConfig(){
        Map<String, Object> config = new HashMap<>();
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host+":9092");
            config.put(ConsumerConfig.GROUP_ID_CONFIG, "user_test");
            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return config;
    }

    @Bean
    public ConsumerFactory<String, Event> consumerFactory(){
        JsonDeserializer<Event> deserializerEvent = new JsonDeserializer<>();
            deserializerEvent.addTrustedPackages("com.example.study.kafka.model");
        return new DefaultKafkaConsumerFactory(consumerConfig(), new StringDeserializer(), deserializerEvent);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Event> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
