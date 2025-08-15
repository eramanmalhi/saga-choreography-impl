package com.lazyprogrammer.payment.consumers;

import com.lazyprogrammer.common.constants.KafkaTopics;
import com.lazyprogrammer.common.events.PaymentCompletedEvent;
import com.lazyprogrammer.common.events.PaymentFailedEvent;
import com.lazyprogrammer.common.events.StockConfirmedEvent;
import com.lazyprogrammer.payment.models.Transaction;
import com.lazyprogrammer.payment.repositories.BankRepository;
import com.lazyprogrammer.payment.repositories.TransactionRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentProcessorConsumer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger log =
            LoggerFactory.getLogger(PaymentProcessorConsumer.class.getName());

    public PaymentProcessorConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = KafkaTopics.STOCK_CONFIRMED, groupId = "payment-group")
    public void handleOrderCreated(ConsumerRecord<String,
            StockConfirmedEvent> record) {
        StockConfirmedEvent event = record.value();
        log.info("Received StockConfirmed: {}", event);
        String transactionId = UUID.randomUUID().toString();
        boolean transactionSuccess = Boolean.FALSE;
        if (BankRepository.checkBalanceAndPay(event.userId(),
                event.paymentAmount())) {
            transactionSuccess = Boolean.TRUE;
        }
        Transaction transaction = new Transaction(transactionId, event.orderId(),
                transactionSuccess, event.userId());
        TransactionRepository.save(transaction);
        if (transactionSuccess) {
            PaymentCompletedEvent paymentCompletedEvent =
                    new PaymentCompletedEvent(event.orderId(), transactionId,
                            transactionSuccess, event.userId());
            kafkaTemplate.send(KafkaTopics.PAYMENT_COMPLETED, paymentCompletedEvent);
            log.info("Sent PaymentCompletedEvent: {}", paymentCompletedEvent);
        } else {
            PaymentFailedEvent paymentFailedEvent = new PaymentFailedEvent(event.orderId(), transactionId,
                    transactionSuccess, event.userId(), event.productId(),
                    event.quantity(),
                    "Transaction Failed, " +
                            "Please try again later.");
            kafkaTemplate.send(KafkaTopics.PAYMENT_FAILED, paymentFailedEvent);
            log.info("Sent PaymentFailedEvent: {}", paymentFailedEvent);
        }
    }

}
