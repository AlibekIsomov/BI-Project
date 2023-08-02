package com.bim.inventory.controller;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.service.InputItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/inputitem")
public class InputItemController {
    @Autowired
    InputItemService itemService;
    @Autowired
    InputItemRepository inputItemRepository;

    @PostMapping
    public InputItem addItem(@RequestBody InputItem item) {
        return itemService.addItem(item);
    }

    @GetMapping
    public List<InputItem> getAllItems() {
    return itemService.getAllItems();
    }

    @GetMapping("/created-between")
    public List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return inputItemRepository.findByCreatedAtBetween(fromDate, toDate);
    }

    @GetMapping("/total-price")
    public double getTotalPrice() {
        return itemService.getTotalPrice();
    }

}