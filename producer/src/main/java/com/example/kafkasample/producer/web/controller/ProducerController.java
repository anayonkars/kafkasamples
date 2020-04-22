package com.example.kafkasample.producer.web.controller;

import com.example.kafkasample.producer.web.request.ProduceMessageRequest;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.example.kafkasample.producer.config.ProducerConfiguration.KAFKA_PRODUCER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/kafka")
public class ProducerController {
    @Autowired
    @Qualifier(KAFKA_PRODUCER)
    private KafkaProducer kafkaProducer;

    @PreDestroy
    public void cleanup() {
        kafkaProducer.close();
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
            return recordMetadata.topic() + ":" + produceMessageRequest.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        } catch (ExecutionException e) {
            return e.getMessage();
        }
    }

}
