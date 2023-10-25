package com.ibs.dockerbacked.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ExecutionException;

@SpringBootTest
public class KafkaTest {
    AdminClient adminClient;
    @Before
    public void init(){
        //kafka topic check
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("linger.ms", 1000);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        adminClient = AdminClient.create(properties);


    }

    @Test
    public void listTopics() throws ExecutionException, InterruptedException {

        KafkaFuture<Set<String>> set = adminClient.listTopics().names();;
        set.get();
    }



    @Test
    public void createTopic() throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic("docker-order1", 1, (short) 1);
        CreateTopicsResult result = adminClient.createTopics(Collections.singleton(newTopic));
        result.all().get();
        System.out.println("ksdf");
    }


}
