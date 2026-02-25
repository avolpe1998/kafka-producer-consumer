package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class OrderManager {
    public void manageOrder(String orderId, String orderStatus) {
        System.out.println("OrderManager - managing orderId: " + orderId + ", orderStatus: " + orderStatus);
    }
}
