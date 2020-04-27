package com.example.kafkasample.producer.message;

public interface MessageProducer {
    String produceMessage(String topic, String message);
}
