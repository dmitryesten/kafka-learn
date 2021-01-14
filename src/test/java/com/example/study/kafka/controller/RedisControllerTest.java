package com.example.study.kafka.controller;

import com.example.study.kafka.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RedisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void setTest() throws Exception {
        Event event = new Event("key-test-redis", "value-key-redis");

        mockMvc.perform(post("/redis/set")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk());

        MvcResult mvcResult =
                mockMvc.perform(get("/redis/get/{key}", event.getKey()))
                        .andExpect(status().isOk())
                        .andReturn();

        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(event.getValue(), mvcResult.getResponse().getContentAsString());
    }


    @Test
    void deleteTest() throws Exception {
        Event event = new Event("key-test-redis", "value-key-redis");

        mockMvc.perform(post("/redis/set")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/redis/delete/{key}", event.getKey()))
                .andExpect(status().isOk());

        MvcResult mvcResult =
                mockMvc.perform(get("/redis/get/{key}", event.getKey()))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertTrue(Optional.ofNullable(mvcResult.getResponse().getContentAsString()).isPresent());

    }
}