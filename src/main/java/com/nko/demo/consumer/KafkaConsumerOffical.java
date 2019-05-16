package com.nko.demo.consumer;

import com.alibaba.fastjson.JSON;
import com.nko.demo.config.KafkaConsumerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

/**
 * @author: nko-sun
 * @Date: 2019/5/8 11:40
 * @Description:
 */
@Component
@EnableScheduling
@Slf4j
public class KafkaConsumerOffical  implements CommandLineRunner {

    @Value("${kafka.distant.topic}")
    String kafkaDistantTopic;


    @Override
    public void run(String... var1) throws Exception {
        Consumer consumer = KafkaConsumerConfiguration.buildConsumer();
        //订阅Topic 支持正则表达式，订阅所有与test相关的Topic  consumer.subscribe("test.*");
        consumer.subscribe(Arrays.asList(kafkaDistantTopic));
        try {
            //heart beat
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : records) {
                    switch (record.partition()) {
                        // partition 0 新增
                        case 0:
                            Map insertMap = JSON.parseObject(record.value());
                            try {

                             //处理业务
                             //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                consumer.commitAsync();
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }

                            break;
                        // partition 1 更新
                        case 1:

                            Map updateMap = JSON.parseObject(record.value());
                            try {
                                //处理业务
                                //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                consumer.commitAsync();
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }

                            break;
                        // partition 2 删除
                        case 2:
                            try {
                                Map deleteMap = JSON.parseObject(record.value());
                                //处理业务
                                //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                consumer.commitAsync();
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }

                            break;
                        default:
                            System.out.println("do nothing");
                    }
                }
            }
        } catch (Exception e) {
            log.error("Unexpected error ", e);
        } finally {
            try {
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }
}
