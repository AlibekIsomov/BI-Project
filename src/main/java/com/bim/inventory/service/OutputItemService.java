package com.bim.inventory.service;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;

import java.time.LocalDateTime;
import java.util.List;

public interface OutputItemService {
    OutputItem addItem(OutputItem item);
    List<OutputItem> getAllItems();


    double getTotalPrice();
    List<OutputItem> getItemsCreatedAfter(LocalDateTime fromDate);
    List<OutputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
