package com.example.kafkareceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerConsumer.class);

    @KafkaListener(topics = "pojo-sending")
    public void receive(Pojo pojo) {
        LOGGER.info("received {}", pojo);
    }

}
