package com.example.kafkasample.producer.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@Configuration
public class ProducerConfiguration {

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

}
