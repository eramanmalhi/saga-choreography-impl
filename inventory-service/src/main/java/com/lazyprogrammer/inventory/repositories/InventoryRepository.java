package com.lazyprogrammer.inventory.repositories;

import com.lazyprogrammer.inventory.models.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryRepository {
    private static final Map<String, Inventory> DB = new HashMap<>();
    private static final Logger log =
            LoggerFactory.getLogger(InventoryRepository.class.getName());

    static {
        populateDB();
    }

    public static Inventory save(Inventory inventory) {
        log.info("Saving inventory {}", inventory);
        DB.put(inventory.productId(), inventory);
        log.info("Saved inventory {}", inventory);
        return inventory;
    }

    public static Inventory getByProductId(String productId) {
        log.info("Retrieving inventory for productId {}", productId);
        return DB.get(productId);
    }

    public static List<Inventory> getAll() {
        return DB.values().stream().toList();
    }

    public static void populateDB() {
        DB.put("101", new Inventory("101", "USB 3.0 Cable - 3 Foot (0.9 " +
                "Meter) - 10 Pack", 5, 1500));
        DB.put("102", new Inventory("102", "3-Button USB Wired Mouse, Black",
                10, 1000));
        DB.put("103", new Inventory("103", "High-Speed HDMI Cable, 3 Feet",
                15, 500));
        DB.put("104", new Inventory("104", "In-Ear Headphones - Black", 20,
                1000));
        DB.put("105", new Inventory("105", "External Hard Drive 1TB", 25,
                1500));
        DB.put("106", new Inventory("106", "Wireless Keyboard and Mouse " +
                "Bundle", 30, 1000));
        DB.put("107", new Inventory("107", "USB 2.0 Cable - 6 Foot (1.8 " +
                "Meters) - 5 Pack", 35, 500));
        DB.put("108", new Inventory("108", "Mini DisplayPort to HDMI Adapter"
                , 40, 1000));
        DB.put("109", new Inventory("109", "Travel Laptop Stand", 45, 1500));
        DB.put("110", new Inventory("110", "3-Outlet Surge Protector with 2 " +
                "USB Ports", 50, 1000));
    }
}
