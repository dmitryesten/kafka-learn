package com.example.study.kafka.controller;

import com.example.study.kafka.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/set")
    public void set(@RequestBody Event event){
        log.info("Set "+ event.toString()+ " to Redis DB");
        redisTemplate.opsForHash().put(event.getKey(), event.getKey().hashCode(), event.getValue());
    }

    @GetMapping("/get/{key}")
    public Optional<Object> get(@PathVariable("key") String key){
        log.info("Get value by "+ key + " of Redis DB");
        return Optional.ofNullable(redisTemplate.opsForHash().get(key, key.hashCode()));
    }

    @DeleteMapping("/delete/{key}")
    public void delete(@PathVariable("key") String key){
        log.info("Delete value by "+ key + " of Redis DB");
        redisTemplate.opsForHash().delete(key, key.hashCode());
    }

    @GetMapping("/entries/{key}")
    public String getEntries(@PathVariable("key") String key){
        return redisTemplate.opsForHash().entries(key).entrySet().toString();
    }

}
