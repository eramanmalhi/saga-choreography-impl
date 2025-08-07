package com.lazyprogrammer.inventory.models;

public record Inventory(String productId, String productName,
                        int qualtityInStock,
                        int price) {
}
