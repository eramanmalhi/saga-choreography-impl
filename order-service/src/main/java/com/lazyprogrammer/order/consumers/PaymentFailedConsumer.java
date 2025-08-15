package com.lazyprogrammer.order.consumers;

import com.lazyprogrammer.common.constants.KafkaTopics;
import com.lazyprogrammer.common.constants.OrderStatus;
import com.lazyprogrammer.common.events.PaymentFailedEvent;
import com.lazyprogrammer.order.models.Order;
import com.lazyprogrammer.order.repositories.OrderRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentFailedConsumer {
    private static final Logger log =
            LoggerFactory.getLogger(PaymentFailedConsumer.class.getName());

    @KafkaListener(topics = KafkaTopics.PAYMENT_FAILED, groupId = "order-group")
    public void handleOrderCreated(ConsumerRecord<String, PaymentFailedEvent> record) {
        PaymentFailedEvent event = record.value();
        log.info("Received PaymentFailedEvent: {}", event);
        Order order = OrderRepository.findById(event.orderId());
        Order updatedOrder = new Order(order.orderId(), order.productId(),
                order.quantity(), OrderStatus.FAILED_PAYMENT, order.userId());
        OrderRepository.save(updatedOrder);
        log.info("All Orders: {}", OrderRepository.getAll());
    }
}
