package com.lazyprogrammer.order.services;

import com.lazyprogrammer.common.constants.KafkaTopics;
import com.lazyprogrammer.common.constants.OrderStatus;
import com.lazyprogrammer.common.events.OrderCreatedEvent;
import com.lazyprogrammer.order.models.Order;
import com.lazyprogrammer.order.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger log =
            LoggerFactory.getLogger(OrderService.class.getName());

    public OrderService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order createOrder(String productId, int quantity, String userId) {
        log.info("Generating orderId");
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, productId, quantity,
                OrderStatus.CREATED, userId);
        OrderRepository.save(order);
        log.info("Generating order created event");
        OrderCreatedEvent orderCreatedEvent =
                new OrderCreatedEvent(orderId, productId, quantity,
                        OrderStatus.CREATED, userId);
        kafkaTemplate.send(KafkaTopics.ORDER_CREATED, orderCreatedEvent);
        log.info("Published order created event: {}", orderCreatedEvent);
        return order;
    }
}
