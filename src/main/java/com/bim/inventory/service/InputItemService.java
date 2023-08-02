package com.bim.inventory.service;

import com.bim.inventory.entity.InputItem;

import java.time.LocalDateTime;
import java.util.List;
public interface InputItemService {
    InputItem addItem(InputItem item);
    List<InputItem> getAllItems();


    double getTotalPrice();
    List<InputItem> getItemsCreatedAfter(LocalDateTime fromDate);
    List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);

}

