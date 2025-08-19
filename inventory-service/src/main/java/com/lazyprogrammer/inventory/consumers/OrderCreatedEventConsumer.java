package com.lazyprogrammer.inventory.consumers;

import com.lazyprogrammer.common.constants.KafkaTopics;
import com.lazyprogrammer.common.events.OrderCreatedEvent;
import com.lazyprogrammer.common.events.StockConfirmedEvent;
import com.lazyprogrammer.common.events.StockUnavailableEvent;
import com.lazyprogrammer.inventory.models.Inventory;
import com.lazyprogrammer.inventory.repositories.InventoryRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventConsumer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger log =
            LoggerFactory.getLogger(OrderCreatedEventConsumer.class.getName());

    public OrderCreatedEventConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED, groupId = "inventory-group")
    public void handlePaymentCompleted(ConsumerRecord<String,
            OrderCreatedEvent> record) {
        OrderCreatedEvent event = record.value();
        log.info("Received OrderCreatedEvent: {}", event);
        int available = InventoryRepository.getAll().stream()
                .filter(product -> product.productId().equalsIgnoreCase(event.productId()))
                .map(Inventory::quantityInStock)
                .findFirst().orElse(-1);
        if (available - event.quantity() < 1) {
            StockUnavailableEvent stockUnavailableEvent =
                    new StockUnavailableEvent(event.productId(),
                            Boolean.FALSE, event.orderId());
            kafkaTemplate.send(KafkaTopics.STOCK_UNAVAILABLE, stockUnavailableEvent);
            log.info("Sent StockUnavailableEvent: {}", stockUnavailableEvent);
        } else {
            Inventory inventory = InventoryRepository.getByProductId(event.productId());
            Inventory updatedInventory = new Inventory(inventory.productId(),
                    inventory.productName(),
                    inventory.quantityInStock() - event.quantity(),
                    inventory.price());
            InventoryRepository.save(updatedInventory);
            StockConfirmedEvent stockConfirmed =
                    new StockConfirmedEvent(inventory.productId(), Boolean.TRUE,
                            event.orderId(), event.userId(),
                            (event.quantity()) * inventory.price(),
                            event.quantity());
            kafkaTemplate.send(KafkaTopics.STOCK_CONFIRMED, stockConfirmed);
            log.info("Sent StockConfirmedEvent: {}", stockConfirmed);
        }
    }
}
