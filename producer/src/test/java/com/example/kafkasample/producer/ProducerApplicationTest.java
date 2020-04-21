package com.example.kafkasample.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProducerApplicationTest {

    @Test
    public void noOpTest() {
        //no op
    }
}
