package com.example.kafkasample.consumer.message;

public interface MessageConsumer {
    String consumeMessage(String topic);
}
