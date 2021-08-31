package com.example.kafkasender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication()
@EnableConfigurationProperties
public class KafkaSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaSenderApplication.class, args);
    }

}
