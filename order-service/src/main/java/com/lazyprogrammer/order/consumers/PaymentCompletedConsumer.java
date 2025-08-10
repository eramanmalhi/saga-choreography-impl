package com.lazyprogrammer.order.consumers;

import com.lazyprogrammer.common.constants.KafkaTopics;
import com.lazyprogrammer.common.constants.OrderStatus;
import com.lazyprogrammer.common.events.PaymentCompletedEvent;
import com.lazyprogrammer.order.models.Order;
import com.lazyprogrammer.order.repositories.OrderRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedConsumer {
    private static final Logger log =
            LoggerFactory.getLogger(PaymentCompletedConsumer.class.getName());

    @KafkaListener(topics = KafkaTopics.PAYMENT_COMPLETED, groupId = "order-group")
    public void handleOrderCreated(ConsumerRecord<String, PaymentCompletedEvent> record) {
        PaymentCompletedEvent event = record.value();
        log.info("Received PaymentCompletedEvent: {}", event);
        Order order = OrderRepository.findById(event.orderId());
        Order updatedOrder = new Order(order.orderId(), order.productId(),
                order.quantity(), OrderStatus.CONFIRMED, order.userId());
        OrderRepository.save(updatedOrder);
        log.info("All Orders: {}", OrderRepository.getAll());
    }
}
