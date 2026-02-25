package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderProducerServiceTest {

    @Test
    public void TestPublishOrderCreatedEvent_SendsCorrectMessageToKafka() {
        // 1. ARRANGE (The Setup)
        // Create a completely fake KafkaTemplate using Mockito
        KafkaTemplate<String, String> mockKafkaTemplate = Mockito.mock(KafkaTemplate.class);

        // Pass the fake template into our real service using standard Java
        OrderProducerService orderProducerService = new OrderProducerService(mockKafkaTemplate);

        String testOrderId = "ORDER-999";
        String expectedTopic = "order-created-topic";
        String expectedPayload = "{\"orderId\": \"ORDER-999\", \"status\": \"CREATED\"}";

        // 2. ACT (The Execution)
        // Call the method we want to test
        orderProducerService.publishOrderCreatedEvent(testOrderId);

        // 3. ASSERT (The Verification)
        // We ask Mockito: "Did the service try to call the .send() method exactly 1 time?"
        // And did it pass the exact topic, key, and JSON string we expected?
        verify(mockKafkaTemplate, times(1)).send(expectedTopic, testOrderId, expectedPayload);
    }
}
