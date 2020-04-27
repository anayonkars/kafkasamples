package com.example.kafkasample.producer.web.controller;

import com.example.kafkasample.producer.message.MessageProducer;
import com.example.kafkasample.producer.web.request.ProduceMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/kafka")
public class ProducerController {

    @Autowired
    @Qualifier("kafkaMessageProducer")
    private MessageProducer kafkaMessageProducer;

    @PostMapping(value = "produce",
                    consumes = {APPLICATION_JSON_VALUE},
                    produces = {APPLICATION_JSON_VALUE})
    public String produce(@RequestBody ProduceMessageRequest produceMessageRequest) {
        return kafkaMessageProducer.produceMessage(produceMessageRequest.getTopic(), produceMessageRequest.getMessage());
    }

}
