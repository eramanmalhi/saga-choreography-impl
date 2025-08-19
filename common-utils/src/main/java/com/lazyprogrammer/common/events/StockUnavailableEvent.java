package com.lazyprogrammer.common.events;

public record StockUnavailableEvent(String productId,
                                    boolean inStock,
                                    String orderId) {
}
