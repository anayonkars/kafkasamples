package com.example.kafkasample.web.controller;

import com.example.kafkasample.web.request.ProduceMessageRequest;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    @Qualifier("kafkaProducer")
    private KafkaProducer kafkaProducer;

    @Autowired
    @Qualifier("kafkaConsumer")
    private KafkaConsumer kafkaConsumer;

    @PreDestroy
    public void cleanup() {
        kafkaProducer.close();
        kafkaConsumer.close();
    }

    @PostMapping(value = "produce",
                    consumes = {APPLICATION_JSON_VALUE},
                    produces = {APPLICATION_JSON_VALUE})
    public String produce(@RequestBody ProduceMessageRequest produceMessageRequest) {
        ProducerRecord producerRecord = new ProducerRecord(produceMessageRequest.getTopic(), produceMessageRequest.getMessage());
        Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send(producerRecord);
        kafkaProducer.flush();
        RecordMetadata recordMetadata;
        try {
            recordMetadata = recordMetadataFuture.get();
            return recordMetadata.toString();
        } catch (InterruptedException e) {
            return e.getMessage();
        } catch (ExecutionException e) {
            return e.getMessage();
        }
    }
}
