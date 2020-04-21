package com.example.kafkasample.consumer.web.controller;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/kafka")
public class ConsumerController {
    @Autowired
    @Qualifier("kafkaConsumer")
    private KafkaConsumer kafkaConsumer;

    @PreDestroy
    public void cleanup() {
        kafkaConsumer.close();
    }

    @GetMapping(value = "consume/{topic}",
            produces = {APPLICATION_JSON_VALUE})
    public String consume(@PathVariable String topic) {
        kafkaConsumer.subscribe(singletonList(topic));
        ConsumerRecords<Long, String> consumerRecords = kafkaConsumer.poll(ofSeconds(1));
        StringBuilder sb = new StringBuilder();
        consumerRecords.forEach(record -> {
            sb.append(record.toString());
        });
        return sb.toString();
    }
}
