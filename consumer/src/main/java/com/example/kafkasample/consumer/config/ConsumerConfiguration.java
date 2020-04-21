package com.example.kafkasample.consumer.config;

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
public class ConsumerConfiguration {

    public static final int ONE = 1;
    public static final String FALSE = "false";
    public static final String EARLIEST = "earliest";

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
        consumerProperties.put(MAX_POLL_RECORDS_CONFIG, ONE);
        consumerProperties.put(ENABLE_AUTO_COMMIT_CONFIG, FALSE);
        consumerProperties.put(AUTO_OFFSET_RESET_CONFIG, EARLIEST);
        return new KafkaConsumer<Long, String>(consumerProperties);
    }
}
