package com.nko.demo.config;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @author: nko-sun
 * @Email : sun7127@126.com
 * @Date: 2019/4/17 12:27
 * @Description: create kafka config bean about producer
 */
@Component
public class KafkaProducerConfiguration {

    @Value("${bootstrap.servers}")
    String bootstrapServersVal;
    @Value("${kafka.producer.acks}")
    String kafkaProAcksVal;
    @Value("${spring.kafka.producer.retries}")
    String kafkaProRetriesVal;
    @Value("${spring.kafka.producer.buffer-memory}")
    String kafkaProBufferMemoryVal;
    @Value("${spring.kafka.producer.key-serializer}")
    String kafkaProKeySerializerVal;
    @Value("${spring.kafka.producer.value-serializer}")
    String kafkaProValueSerializerVal;
    @Value("${spring.kafka.producer.transactional.id}")
    String kafkaProTranscationalIdVal;
    @Value("${spring.kafka.producer.enable.idempotence}")
    String kafkaProEnableIdempotenceVal;
    @Value("${spring.kafka.producer.client-id}")
    String kafkaProClientIdVal;


    static String bootstrapServers;
    static String kafkaProAcks;
    static String kafkaProRetries;
    static String kafkaProBufferMemory;
    static String kafkaProKeySerializer;
    static String kafkaProValueSerializer;
    static String kafkaProTranscationalId;
    static String kafkaProEnableIdempotence;
    static String kafkaProClientId;


    @PostConstruct
    public void init() {
        bootstrapServers =bootstrapServersVal;
        kafkaProAcks=kafkaProAcksVal;
        kafkaProRetries=kafkaProRetriesVal;
        kafkaProBufferMemory=kafkaProBufferMemoryVal;
        kafkaProKeySerializer=kafkaProKeySerializerVal;
        kafkaProValueSerializer=kafkaProValueSerializerVal;
        kafkaProTranscationalId=kafkaProTranscationalIdVal;
        kafkaProEnableIdempotence=kafkaProEnableIdempotenceVal;
        kafkaProClientId=kafkaProClientIdVal;
    }


    /**
     * //事务初始化一次
     *
     * @return Producer
     */

    public static Producer buildProducer() {
        Properties props = new Properties();
        //broker地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //请求时候需要验证
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProAcks);
        //请求失败时候需要重试
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProRetries);
        //内存缓存区大小
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProBufferMemory);
        //指定消息key序列化方式
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaProKeySerializer);
        //指定消息本身的序列化方式
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                kafkaProValueSerializer);
        // 设置事务id
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, kafkaProTranscationalId);
        // 设置幂等性
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafkaProEnableIdempotence);
        // client-id 更换，则重新消费
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProClientId);
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        //初始事务
        producer.initTransactions();
        //开始事务
        producer.beginTransaction();
        return producer;
    }

}