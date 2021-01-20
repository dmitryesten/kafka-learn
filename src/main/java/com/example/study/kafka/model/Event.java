package com.example.study.kafka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@NoArgsConstructor
public class Event {
    @NonNull
    private String key;
    @NonNull
    private String value;
    @Getter
    private final LocalDateTime localDateTime = LocalDateTime.now();
}
