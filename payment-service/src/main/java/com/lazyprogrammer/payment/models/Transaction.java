package com.lazyprogrammer.payment.models;

public record Transaction(String transactionId,
                          String orderId,
                          boolean isSuccessful,
                          String userId) {
}
