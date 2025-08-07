package com.lazyprogrammer.inventory.controllers;

import com.lazyprogrammer.inventory.models.Inventory;
import com.lazyprogrammer.inventory.repositories.InventoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @GetMapping
    public List<Inventory> getAll() {
        return (List<Inventory>) InventoryRepository.getAll();
    }

}
