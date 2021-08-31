package com.example.kafkasender;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class Config {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootsrapServer;

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootsrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, "600"); //default 0 (ignored if acs=0) causes message to be out of order
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "100"); //default 100
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5"); //default 5 (1 to make messages in order, impacts throughput)
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "1600"); // high batch size and linger = high throughput
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "5");
        return properties;
    }

    @Bean
    public ProducerFactory<String, Pojo> producerFactory() {
        return new DefaultKafkaProducerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Pojo> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
