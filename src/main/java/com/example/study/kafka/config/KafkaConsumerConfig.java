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
    public Map<String, Object> consumerCommonConfig(){
        Map<String, Object> config = new HashMap<>();
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host+":9092");
            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return config;
    }

    @Bean
    public ConsumerFactory<String, String> consumerStringFactory(){
        Map<String, Object> specialConfig = consumerCommonConfig();
            specialConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "user_test_string");
            specialConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory(specialConfig, new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerStringFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Event> consumerObjectFactory(){
        Map<String, Object> specialConfig = consumerCommonConfig();
            specialConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "user_test");
            specialConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        JsonDeserializer<Event> deserializerEvent = new JsonDeserializer<>();
            deserializerEvent.addTrustedPackages("com.example.study.kafka.model");
        return new DefaultKafkaConsumerFactory(specialConfig, new StringDeserializer(), deserializerEvent);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerObjectContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Event> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerObjectFactory());
        return factory;
    }

}
