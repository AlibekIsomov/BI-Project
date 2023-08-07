package com.bim.inventory.service;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;
public interface InputItemService {
    InputItem addItem(InputItem item);
    List<InputItem> getAllItems();
    public InputItem update(InputItem entity);
    double getTotalPrice();

    public boolean delete(Long id);
    public InputItem getById(Long id);
    List<InputItem> getItemsCreatedAfter(LocalDateTime fromDate);
    List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);

}

