package com.example.study.kafka.model;

import lombok.*;
import java.time.LocalDateTime;


@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Event {
    @NonNull
    private String key;
    @NonNull
    private String value;
    @Getter
    private final LocalDateTime localDateTime = LocalDateTime.now();
}
