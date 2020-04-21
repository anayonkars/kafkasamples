import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

public class Main {

    public static final String KAFKA_BROKERS = "localhost:9092";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer producer = new KafkaProducer(properties);
        try {
            ProducerRecord myMessage = new ProducerRecord("mytopic", "myMessage");
            producer.send(myMessage);
            System.out.println("Sent " + myMessage);
            ProducerRecord myMessage2 = new ProducerRecord("mytopic", 0, System.currentTimeMillis(), "mykey", "myValue");
            producer.send(myMessage2);
            System.out.println("Sent " + myMessage2);
        } finally {
            producer.close();
        }
    }
}
