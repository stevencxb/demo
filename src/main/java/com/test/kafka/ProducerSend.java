package com.test.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Title: ProducerSend
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-02-22 17:37
 * @Version V1.0
 */
public class ProducerSend {

    public static void main(String [] args){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.166.1.211:9092,192.166.1.212:9092,192.166.1.213:9092");
//        props.put("acks", "all");
//        props.put("delivery.timeout.ms", 30000);
//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0 ; i < 10 ; i ++ ){
            producer.send(new ProducerRecord<String, String>("topic01", "hello--"+i));
//            producer.send(new ProducerRecord<String, String>("topic01", "key--"+i,"hello--"+i));
        }

        producer.close();

    }


}
