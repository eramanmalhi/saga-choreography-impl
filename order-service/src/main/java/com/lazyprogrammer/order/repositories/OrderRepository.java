package com.lazyprogrammer.order.repositories;

import com.lazyprogrammer.order.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {
    private static final Map<String, Order> DB = new HashMap<>();
    private static final Logger log =
            LoggerFactory.getLogger(OrderRepository.class.getName());

    public static Order save(Order order) {
        log.info("Saving order {}", order);
        DB.put(order.orderId(), order);
        log.info("Saved order {}", order);
        return order;
    }

    public static Order findById(String orderId) {
        return DB.get(orderId);
    }

    public static List<Order> getAll() {
        return DB.values().stream().toList();
    }
}
