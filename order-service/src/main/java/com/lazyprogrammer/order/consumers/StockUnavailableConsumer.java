package com.lazyprogrammer.order.consumers;

import com.lazyprogrammer.common.constants.KafkaTopics;
import com.lazyprogrammer.common.constants.OrderStatus;
import com.lazyprogrammer.common.events.StockUnavailableEvent;
import com.lazyprogrammer.order.models.Order;
import com.lazyprogrammer.order.repositories.OrderRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class StockUnavailableConsumer {
    private static final Logger log =
            LoggerFactory.getLogger(StockUnavailableConsumer.class.getName());

    @KafkaListener(topics = KafkaTopics.STOCK_UNAVAILABLE, groupId = "order-group")
    public void handleOrderCreated(ConsumerRecord<String, StockUnavailableEvent> record) {
        StockUnavailableEvent event = record.value();
        log.info("Received StockUnavailableEvent: {}", event);
        Order order = OrderRepository.findById(event.orderId());
        Order updatedOrder = new Order(order.orderId(), order.productId(),
                order.quantity(), OrderStatus.FAILED_STOCK_UNAVAILABLE,
                order.userId());
        OrderRepository.save(updatedOrder);
        log.info("All Orders: {}", OrderRepository.getAll());
    }
}
