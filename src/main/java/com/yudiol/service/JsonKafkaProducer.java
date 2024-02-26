package com.yudiol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object object) {
        Message<Object> message = MessageBuilder
                .withPayload(object)
                .setHeader(KafkaHeaders.TOPIC, "phrase_json")
                .build();
        kafkaTemplate.send(message);
    }
}
