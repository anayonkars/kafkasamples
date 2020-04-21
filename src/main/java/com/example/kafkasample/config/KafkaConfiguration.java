package com.example.kafkasample.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@Configuration
public class KafkaConfiguration {
    @Bean
    @Qualifier("kafkaProducer")
    public KafkaProducer kafkaProducer(@Value("${kafka.brokers}") String kafka_brokers) {
        Properties producerProperties = new Properties();
        producerProperties.put(BOOTSTRAP_SERVERS_CONFIG, kafka_brokers);
        String stringSerializerClassName = StringSerializer.class.getName();
        producerProperties.put(KEY_SERIALIZER_CLASS_CONFIG, stringSerializerClassName);
        producerProperties.put(VALUE_SERIALIZER_CLASS_CONFIG, stringSerializerClassName);
        return new KafkaProducer<Long, String>(producerProperties);
    }

    @Bean
    @Qualifier("kafkaConsumer")
    public KafkaConsumer kafkaConsumer(@Value("${kafka.brokers}") String kafka_brokers,
                                       @Value("${kafka.consumerGroup}") String consumerGroup) {
        Properties consumerProperties = new Properties();
        consumerProperties.put(BOOTSTRAP_SERVERS_CONFIG, kafka_brokers);
        String stringDeserializerClassName = StringDeserializer.class.getName();
        consumerProperties.put(KEY_DESERIALIZER_CLASS_CONFIG, stringDeserializerClassName);
        consumerProperties.put(VALUE_DESERIALIZER_CLASS_CONFIG, stringDeserializerClassName);
        consumerProperties.put(GROUP_ID_CONFIG, consumerGroup);
        consumerProperties.put(MAX_POLL_RECORDS_CONFIG, 1);
        consumerProperties.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaConsumer<Long, String>(consumerProperties);
    }
}
