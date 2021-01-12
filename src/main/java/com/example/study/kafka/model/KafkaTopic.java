package com.example.study.kafka.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.KafkaAdminClient;

@Data
@RequiredArgsConstructor
public class KafkaTopic {
    @NonNull
    private String name;
    @NonNull
    private Integer partition;
    @NonNull
    private Short replication;

}
