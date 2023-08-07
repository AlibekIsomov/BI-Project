package com.bim.inventory.service;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;

import java.time.LocalDateTime;
import java.util.List;

public interface OutputItemService {
    OutputItem addItem(OutputItem item);
    List<OutputItem> getAllItems();
    public OutputItem update(OutputItem entity);
    public boolean delete(Long id);
    double getTotalPrice();
    List<OutputItem> getItemsCreatedAfter(LocalDateTime fromDate);
    List<OutputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);
    public OutputItem getById(Long id);
}
