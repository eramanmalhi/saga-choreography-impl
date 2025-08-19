package com.lazyprogrammer.common.events;

public record PaymentCompletedEvent(String orderId,
                                    String paymentId,
                                    boolean isSuccessful,
                                    String userId) {
}
