package com.nko.demo.producer;

import com.nko.demo.config.KafkaProducerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author: nko-sun
 * @Date: 2019/5/8 10:28
 * @Description:
 */

@Component
@EnableScheduling
@Slf4j
public class KafkaOfficeProducer {

    @Scheduled(cron = "00/2 * * * * ?")
    public void produceIdempotMessage() {
        // 创建Producer
        String message = UUID.randomUUID().toString();
//        ListenableFuture future = kafkaTemplate.send("sunqi", message);
        Producer producer = KafkaProducerConfiguration.buildProducer();
        producer.initTransactions();
        producer.beginTransaction();
        // 发送消息
        try {
            producer.send(new ProducerRecord<String, String>("officeProduct", 0,System.currentTimeMillis(),"test",message));
            producer.commitTransaction();
        }catch (Exception e){
            producer.abortTransaction();
        }
    }

}
