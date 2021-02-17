//package cn.xjjdog.bcmall.config.kafka;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaAdmin;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Copyright (c) 2021. All Rights Reserved.
// * <p>
// * Kafka基础配置
// *
// * @author xjjdog
// */
//
//@Configuration
//@RequiredArgsConstructor
//public class KafkaTopicConfig {
//    @Value(value = "${bcmall.kafka.bootstrapAddress}")
//    private String bootstrapAddress;
//
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        return new KafkaAdmin(configs);
//    }
//
//
//    @Bean
//    public NewTopic topicStockEvent() {
//        return new NewTopic(Constant.TopicStockEvent,
//                Constant.DefaultPartitionNumber,
//                Constant.DefaultReplicationFactor);
//    }
//
//}
//
