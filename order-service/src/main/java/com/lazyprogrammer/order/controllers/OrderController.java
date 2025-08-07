package com.lazyprogrammer.order.controllers;

import com.lazyprogrammer.order.models.Order;
import com.lazyprogrammer.order.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class.getName());

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestParam(name = "productId") String productId,
                             @RequestParam(name = "quantity") int quantity,
                             @RequestParam(name = "userId") String userId) {
        log.info("Creating order for product {} with quantity {} from {}",
                productId,
                quantity, userId);
        return orderService.createOrder(productId, quantity, userId);
    }
}
