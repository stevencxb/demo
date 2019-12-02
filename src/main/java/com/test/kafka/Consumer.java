//package com.test.kafka;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.TopicPartition;
//
//import java.util.Arrays;
//import java.util.Properties;
//
///**
// * @Title: Consumer
// * @Description: TODO
// * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
// * @Date 2019-02-25 9:33
// * @Version V1.0
// */
//public class Consumer {
//
//    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers",
//                "192.166.1.211:9092,192.166.1.212:9092,192.166.1.213:9092");// 该地址是集群的子集，用来探测集群。
//        props.put("group.id", "test2");// cousumer的分组id
//        props.put("enable.auto.commit", "false");// 自动提交offsets
////        props.put("auto.commit.interval.ms", "1000");// 每隔1s，自动提交offsets
//        props.put("session.timeout.ms", "30000");// Consumer向集群发送自己的心跳，超时则认为Consumer已经死了，kafka会把它的分区分配给其他进程
//        props.put("key.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");// 反序列化器
//        props.put("value.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        consumer.subscribe(Arrays.asList("topic01"));// 订阅的topic,可以多个
////        consumer.assign(Arrays.asList(new TopicPartition("topic01",0)));
//
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(100);
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.printf("offset = %d, key = %s, value = %s, partition = %d" ,
//                        record.offset(), record.key(), record.value(),record.partition());
//                System.out.println();
//                consumer.commitAsync();
//            }
//        }
//    }
//
//}
