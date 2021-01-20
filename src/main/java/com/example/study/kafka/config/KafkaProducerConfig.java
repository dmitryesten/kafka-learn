package com.example.study.kafka.config;

import com.example.study.kafka.model.Event;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value("${remote.host}")
    private String host;

    @Bean
    public Map<String, Object> producerCommonConfig(){
        Map<String, Object> config = new HashMap<>();
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host + ":9092");
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return config;
    }

    @Bean
    public ProducerFactory<String, String> producerStringFactory(){
        Map<String, Object> specialConfig = producerCommonConfig();
            specialConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(specialConfig);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaStringTemplate(){
        return new KafkaTemplate<>(producerStringFactory());
    }

    @Bean @Primary
    public ProducerFactory<String, Event> producerObjectFactory(){
        Map<String, Object> specialConfig = producerCommonConfig();
            specialConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(specialConfig);
    }

    @Bean @Primary
    public KafkaTemplate<String, Event> kafkaObjectTemplate(){
        return new KafkaTemplate<>(producerObjectFactory());
    }


}
