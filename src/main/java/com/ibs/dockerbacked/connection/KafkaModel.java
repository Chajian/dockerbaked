package com.ibs.dockerbacked.connection;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.KafkaFuture;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * kafka模块
 * @Date 2023/11/03
 */
@Slf4j
@Data
public class KafkaModel {

    //kafka config
    Properties props = new Properties();
    Properties summerprops = new Properties();
    Producer<Long, String> producer;
    KafkaConsumer<Long, String> consumer;

    String kafkaIp;
    String port;

    public KafkaModel(String kafkaIp, String port) {
        this.kafkaIp = kafkaIp;
        this.port = port;
        init();
    }

    public void init (){

        //kafka topic check
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaIp+":"+port);
        properties.put("linger.ms", 1000);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        AdminClient adminClient = AdminClient.create(properties);
        KafkaFuture<Set<String>> set = adminClient.listTopics().names();;
        try {
            set.get();
            if(!set.get().contains("docker-order")){
                //创建主题
                NewTopic newTopic = new NewTopic("docker-order", 1, (short) 1);
                CreateTopicsResult result = adminClient.createTopics(Collections.singleton(newTopic));
                result.all().get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        //kafka config
        props.put("bootstrap.servers", kafkaIp+":"+port);
        props.put("linger.ms", 1000);
        props.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);

        summerprops.setProperty("bootstrap.servers", kafkaIp+":"+port);
        summerprops.setProperty("group.id", "test");
        summerprops.setProperty("enable.auto.commit", "true");
        summerprops.setProperty("auto.commit.interval.ms", "1000");
        summerprops.setProperty("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        summerprops.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(summerprops);
        consumer.subscribe(Arrays.asList("docker-order"));
    }
}
