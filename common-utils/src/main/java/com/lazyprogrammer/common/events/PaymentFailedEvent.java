package com.lazyprogrammer.common.events;

public record PaymentFailedEvent(String orderId, String paymentId,
                                 boolean isSuccessful, String userId,
                                 String productId, int quantity,
                                 String details) {
}
