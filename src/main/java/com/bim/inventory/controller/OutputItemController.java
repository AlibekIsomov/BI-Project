package com.bim.inventory.controller;


import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.InputItemService;
import com.bim.inventory.service.OutputItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/outputitem")
public class OutputItemController {
    @Autowired
    OutputItemService itemService;
    @Autowired
    OutputItemRepository outputItemRepository;

    @PostMapping
    public OutputItem addItem(@RequestBody OutputItem item) {
        return itemService.addItem(item);
    }

    @GetMapping
    public List<OutputItem> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/created-between")
    public List<OutputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return outputItemRepository.findByCreatedAtBetween(fromDate, toDate);
    }

    @GetMapping("/total-price")
    public double getTotalPrice() {
        return itemService.getTotalPrice();
    }
}
