package com.example.kafkasender;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@RestController
public class Controller {


    private final KafkaTemplate<String, Pojo> template;

    public Controller(KafkaTemplate<String, Pojo> template) {
        this.template = template;
    }

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        createAndSendMessage("1", what);
    }

    @PostMapping(path = "/send/alot")
    public void sendFoo() {
        sendALot();
    }

    private ListenableFuture<SendResult<String, Pojo>> createAndSendMessage(String key, String value) {
        ProducerRecord<String, Pojo> producerRecord = new ProducerRecord<>("pojo-sending", key, new Pojo(value));
        return template.send(producerRecord);
    }

    private void sendALot() {
        String generatedString = randomString();
        Stream.iterate(0, n -> n + 1)
                .parallel() // message will be sent at random
                .limit(100)
                .forEach(n -> createAndSendMessage(generatedString, generatedString+"_"+ n ));
    }

    private String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
