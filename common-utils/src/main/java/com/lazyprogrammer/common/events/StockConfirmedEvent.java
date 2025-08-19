package com.lazyprogrammer.common.events;

public record StockConfirmedEvent(String productId,
                                  boolean inStock,
                                  String orderId,
                                  String userId,
                                  int paymentAmount,
                                  int quantity) {
}
