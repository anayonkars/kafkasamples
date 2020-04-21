import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

public class Main {

    public static final String KAFKA_BROKERS = "localhost:9092";
    public static final String KAFKA_TOPIC = "mytopic";

    public static void main(String[] args) {
        Properties producerProperties = new Properties();
        producerProperties.put(BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        producerProperties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer producer = new KafkaProducer(producerProperties);
        Properties consumerProperties = new Properties();
        consumerProperties.put(BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        consumerProperties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(GROUP_ID_CONFIG, "myconsumergroup");
        consumerProperties.put(MAX_POLL_RECORDS_CONFIG, 10);
        consumerProperties.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<Long, String> consumer = new KafkaConsumer(consumerProperties);
        try {
            consumer.subscribe(Collections.singletonList(KAFKA_TOPIC));
            ProducerRecord myMessage = new ProducerRecord(KAFKA_TOPIC, "myMessage");
            producer.send(myMessage);
            System.out.println("Sent " + myMessage);
            ProducerRecord myMessage2 = new ProducerRecord(KAFKA_TOPIC, "myMessage2");
            producer.send(myMessage2);
            System.out.println("Sent " + myMessage2);
            ProducerRecord myMessage3 = new ProducerRecord(KAFKA_TOPIC, 0, System.currentTimeMillis(), "mykey", "myValue");
            producer.send(myMessage3);
            System.out.println("Sent " + myMessage3);
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            consumerRecords.forEach(record -> {
                System.out.println(record.toString());
            });
            consumer.position(new TopicPartition(KAFKA_TOPIC, 0), Duration.ofSeconds(1));
        } finally {
            producer.close();
            consumer.close();
        }
    }
}
