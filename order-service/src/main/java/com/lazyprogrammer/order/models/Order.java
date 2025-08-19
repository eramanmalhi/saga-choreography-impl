package com.lazyprogrammer.order.models;

public record Order(String orderId,
                    String productId,
                    int quantity,
                    String status,
                    String userId) {
}
