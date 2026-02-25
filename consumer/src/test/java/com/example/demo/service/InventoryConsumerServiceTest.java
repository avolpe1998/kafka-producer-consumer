package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class InventoryConsumerServiceTest {

    @Test
    public void TestConsumeOrderEvent_SendsCorrectParamsToOrderManager(){
        OrderManager orderManager = Mockito.mock(OrderManager.class);
        InventoryConsumerService inventoryConsumerService = new InventoryConsumerService(orderManager);

        String messagePayloadTest = "{\"orderId\": \"ORDER-999\", \"status\": \"CREATED\"}";

        inventoryConsumerService.consumeOrderEvent(messagePayloadTest);

        verify(orderManager, times(1)).manageOrder("ORDER-999", "CREATED");
    }

    @Test
    public void TestConsumeOrderEvent_WithBadOrderId_ThrowsRunTimeException(){
        OrderManager orderManager = Mockito.mock(OrderManager.class);
        InventoryConsumerService inventoryConsumerService = new InventoryConsumerService(orderManager);

        String wrongMessagePayloadTest = "{\"orderrrId\": \"ORDER-999\", \"status\": \"CREATED\"}";

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            inventoryConsumerService.consumeOrderEvent(wrongMessagePayloadTest);
        });

        assertTrue(thrownException.getMessage().contains("Failed to parse Kafka message orderId:"));

        Mockito.verifyNoInteractions(orderManager);
    }

    @Test
    public void TestConsumeOrderEvent_WithBadStatus_ThrowsRunTimeException(){
        OrderManager orderManager = Mockito.mock(OrderManager.class);
        InventoryConsumerService inventoryConsumerService = new InventoryConsumerService(orderManager);

        String wrongMessagePayloadTest = "{\"orderId\": \"ORDER-999\", \"statusss\": \"CREATED\"}";

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            inventoryConsumerService.consumeOrderEvent(wrongMessagePayloadTest);
        });

        assertTrue(thrownException.getMessage().contains("Failed to parse Kafka message status:"));

        Mockito.verifyNoInteractions(orderManager);
    }
}
