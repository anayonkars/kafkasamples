package com.example.kafkasample.consumer.message;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

import static com.example.kafkasample.consumer.config.ConsumerConfiguration.KAFKA_CONSUMER;
import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;

@Component
@Qualifier("kafkaMessageConsumer")
public class KafkaMessageConsumer  implements MessageConsumer {

    @Autowired
    @Qualifier(KAFKA_CONSUMER)
    private KafkaConsumer kafkaConsumer;

    @PreDestroy
    public void cleanup() {
        kafkaConsumer.close();
    }

    @Override
    public String consumeMessage(String topic) {
        kafkaConsumer.subscribe(singletonList(topic));
        ConsumerRecords<Long, String> consumerRecords = kafkaConsumer.poll(ofSeconds(1));
        StringBuilder sb = new StringBuilder();
        consumerRecords.forEach(record -> {
            sb.append(record.toString());
        });
        return sb.toString();
    }
}
