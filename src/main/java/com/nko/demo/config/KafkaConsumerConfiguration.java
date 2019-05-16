package com.nko.demo.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @author: sun
 * @Email : sun7127@126.com
 * @Date: 2019/4/17 12:27
 * @Description: create kafka bean  about consumer
 */
@Component
public class KafkaConsumerConfiguration {


    @Value("${bootstrap.servers}")
    String bootstrapServersVal;
    @Value("${spring.kafka.consumer.group-id}")
    String kafkaConsumerGroupIdVal;
    @Value("${spring.kafka.consumer.client-id}")
    String kafkaConsumerClientIdVal;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    String kafkaConsumerAutoOffsetResetVal;
    @Value("${spring.kafka.consumer.isolation.level}")
    String kafkaConsumerIsolationLevelVal;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    String kafkaConsumerEnableAutoCommitVal;
    @Value("${spring.kafka.consumer.session.timeout.ms}")
    String kafkaConsumerSessionTimeOutMsVal;
    @Value("${spring.kafka.consumer.key-deserializer}")
    String kafkaConsumerKeydeserializerVal;
    @Value("${spring.kafka.consumer.value-deserializer}")
    String kafkaConsumerValuedeserializerVal;


    static String bootstrapServers;
    static String kafkaConsumerGroupId;
    static String kafkaConsumerClientId;
    static String kafkaConsumerAutoOffsetReset;
    static String kafkaConsumerIsolationLevel;
    static String kafkaConsumerEnableAutoCommit;
    static String kafkaConsumerSessionTimeOutMs;
    static String kafkaConsumerKeydeserializer;
    static String kafkaConsumerValuedeserializer;

    @PostConstruct
    public void init() {
        bootstrapServers = bootstrapServersVal;
        kafkaConsumerGroupId=kafkaConsumerGroupIdVal;
        kafkaConsumerClientId=kafkaConsumerClientIdVal;
        kafkaConsumerAutoOffsetReset=kafkaConsumerAutoOffsetResetVal;
        kafkaConsumerIsolationLevel=kafkaConsumerIsolationLevelVal;
        kafkaConsumerEnableAutoCommit=kafkaConsumerEnableAutoCommitVal;
        kafkaConsumerSessionTimeOutMs=kafkaConsumerSessionTimeOutMsVal;
        kafkaConsumerKeydeserializer=kafkaConsumerKeydeserializerVal;
        kafkaConsumerValuedeserializer=kafkaConsumerValuedeserializerVal;
    }


    public static Consumer<String, String> buildConsumer() {
        Properties props = new Properties();
        // bootstrap.servers是Kafka集群的IP地址。多个时,使用逗号隔开

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 消费者群组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerGroupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaConsumerClientId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerAutoOffsetReset);
        // 设置隔离级别
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, kafkaConsumerIsolationLevel);
        // 关闭自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerEnableAutoCommit);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConsumerSessionTimeOutMs);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerKeydeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                kafkaConsumerValuedeserializer
        );
        KafkaConsumer<String, String> consumer = new KafkaConsumer
                <String, String>(props);
        return consumer;
    }

}