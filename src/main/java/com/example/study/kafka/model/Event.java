package com.example.study.kafka.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
public class Event {
    @NonNull
    private String key;
    @NonNull
    private String value;
    private LocalDateTime localDateTime = LocalDateTime.now();

}
