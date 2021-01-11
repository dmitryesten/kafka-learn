package com.example.study.kafka.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event {

    private String key;
    private String value;
    private LocalDateTime localDateTime;

    public Event(String key, String value) {
        this.key = key;
        this.value = value;
        this.localDateTime = LocalDateTime.now();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
