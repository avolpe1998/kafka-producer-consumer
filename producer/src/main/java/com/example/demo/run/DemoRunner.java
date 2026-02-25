package com.example.demo.run;

import com.example.demo.service.OrderProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {
    private final OrderProducerService orderProducerService;

    public DemoRunner(OrderProducerService orderProducerService) {
        this.orderProducerService = orderProducerService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 App started! Sending demo messages to Kafka...");

        for (int i = 0; i < 3; i++) {
            int randomNum = (int) (Math.random() * 9000) + 1000;
            orderProducerService.publishOrderCreatedEvent("ORDER-" + randomNum);
        }
    }
}
