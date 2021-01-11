package com.example.study.kafka.controller;

import com.example.study.kafka.model.KafkaTopic;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewTopic springTopic;

    @Test
    public void createTopic() throws Exception {
        KafkaTopic kafkaTopicTest = new KafkaTopic("new-topic-test", 1, (short) 1);

        mockMvc.perform(
                    post("/admin/create/topic")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(kafkaTopicTest)))
                .andExpect(status().isOk());

        MvcResult mvcResult = this.mockMvc.perform(
                get("/admin/topics/{nameTopic}", "new-topic-test"))
                .andReturn();

        Assertions.assertEquals(kafkaTopicTest.getName(), mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void createTopics() throws Exception {
        List<String> kafkaTopicsListTest =
                List.of(objectMapper.writeValueAsString(new KafkaTopic("new-topic-test-0", 1, (short) 1)),
                        objectMapper.writeValueAsString(new KafkaTopic("new-topic-test-1", 1, (short) 1)));

        MvcResult mvcResult =
                mockMvc.perform(
                post("/admin/create/topics")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(String.valueOf(kafkaTopicsListTest)))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("new-topic-test-0"));
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("new-topic-test-1"));

    }

    @Test
    public void getTopics() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(get("/admin/topics"))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains(springTopic.name()));
    }

    @Test
    public void getTopicByName() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(get("/admin/topics/{nameTopic}", springTopic.name()))
                        .andExpect(status().isOk()).andReturn();

        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains(springTopic.name()));
    }

}