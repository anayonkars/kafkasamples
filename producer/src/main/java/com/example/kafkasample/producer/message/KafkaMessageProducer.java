package com.example.kafkasample.producer.message;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.example.kafkasample.producer.config.ProducerConfiguration.KAFKA_PRODUCER;

@Component
@Qualifier("kafkaMessageProducer")
public class KafkaMessageProducer implements MessageProducer {

    @Autowired
    @Qualifier(KAFKA_PRODUCER)
    private KafkaProducer kafkaProducer;

    @PreDestroy
    public void cleanup() {
        kafkaProducer.close();
    }

    @Override
    public String produceMessage(String topic, String message) {
        ProducerRecord producerRecord = new ProducerRecord(topic, message);
        Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send(producerRecord);
        kafkaProducer.flush();
        RecordMetadata recordMetadata;
        try {
            recordMetadata = recordMetadataFuture.get();
            return recordMetadata.topic() + ":" + message;
        } catch (InterruptedException e) {
            return e.getMessage();
        } catch (ExecutionException e) {
            return e.getMessage();
        }
    }
}
