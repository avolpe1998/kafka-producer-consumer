package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumerService {

    private final OrderManager orderManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InventoryConsumerService(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    // Spring listens to this topic and passes the message payload into this method
    @KafkaListener(topics = "order-created-topic", groupId = "inventory-service-group")
    public void consumeOrderEvent(String message){
        System.out.println("InventoryConsumerService - Received Order Event from Kafka: " + message);

        // Here is where your business logic happens!
        // e.g., Parse the JSON string, extract the orderId, and reserve the items in the database.
        String orderId = parseJsonAndGetOrderId(message);
        String orderStatus = parseJsonAndGetOrderStatus(message);

        orderManager.manageOrder(orderId, orderStatus);
    }

    private String parseJsonAndGetOrderId(String jsonString) {

        try {
            // 2. Read the raw string into a JSON Tree
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // 3. Grab the specific field you want and convert it to text
            return rootNode.get("orderId").asText();

        } catch (Exception e) {
            // Always handle the case where the JSON is broken or malformed!
            throw new RuntimeException("Failed to parse Kafka message orderId: " + jsonString, e);
        }
    }

    private String parseJsonAndGetOrderStatus(String jsonString) {

        try {
            // 2. Read the raw string into a JSON Tree
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // 3. Grab the specific field you want and convert it to text
            return rootNode.get("status").asText();

        } catch (Exception e) {
            // Always handle the case where the JSON is broken or malformed!
            throw new RuntimeException("Failed to parse Kafka message status: " + jsonString, e);
        }
    }
}
