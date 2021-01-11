package com.example.study.kafka.model;

import org.apache.kafka.clients.admin.KafkaAdminClient;

public class KafkaTopic {

    private String name;
    private Integer partition;
    private Short replication;

    public KafkaTopic(){}

    public KafkaTopic(String name, Integer partition, Short replication) {
        this.name = name;
        this.partition = partition;
        this.replication = replication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Short getReplication() {
        return replication;
    }

    public void setReplication(Short replication) {
        this.replication = replication;
    }

}
