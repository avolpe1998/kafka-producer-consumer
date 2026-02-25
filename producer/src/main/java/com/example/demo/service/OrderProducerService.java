package com.example.demo.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerService {
    // Spring automatically injects this based on your application.yml
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_NAME = "order-created-topic";

    public OrderProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreatedEvent(String orderId){
        String messagePayload = String.format("{\"orderId\": \"%s\", \"status\": \"CREATED\"}", orderId);

        kafkaTemplate.send(TOPIC_NAME, orderId, messagePayload);

        System.out.println("Message sent to Kafka topic: " + messagePayload);
    }
}
