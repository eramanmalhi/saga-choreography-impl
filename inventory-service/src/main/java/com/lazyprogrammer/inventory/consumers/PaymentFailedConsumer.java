package com.lazyprogrammer.inventory.consumers;

import com.lazyprogrammer.common.constants.KafkaTopics;
import com.lazyprogrammer.common.events.PaymentFailedEvent;
import com.lazyprogrammer.inventory.models.Inventory;
import com.lazyprogrammer.inventory.repositories.InventoryRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class PaymentFailedConsumer {
    private static final Logger log =
            LoggerFactory.getLogger(PaymentFailedConsumer.class.getName());

    @KafkaListener(topics = KafkaTopics.PAYMENT_FAILED, groupId = "inventory-group")
    public void handleOrderCreated(ConsumerRecord<String, PaymentFailedEvent> record) {
        PaymentFailedEvent event = record.value();
        log.info("Received PaymentFailedEvent: {}", event);
        Inventory inventory = InventoryRepository.getByProductId(event.productId());
        Inventory updatedInventory = new Inventory(inventory.productId(),
                inventory.productName(),
                inventory.qualtityInStock() + event.quantity(),
                inventory.price());
        InventoryRepository.save(updatedInventory);
    }
}
