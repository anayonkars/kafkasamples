package com.example.kafkasample.consumer.web.controller;

import com.example.kafkasample.consumer.message.MessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/kafka")
public class ConsumerController {

    @Autowired
    @Qualifier("kafkaMessageConsumer")
    MessageConsumer kafkaMessageConsumer;

    @GetMapping(value = "consume/{topic}",
            produces = {APPLICATION_JSON_VALUE})
    public String consume(@PathVariable String topic) {
        return kafkaMessageConsumer.consumeMessage(topic);
    }
}
