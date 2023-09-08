package com.bim.inventory.service;

import com.bim.inventory.entity.OutputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OutputItemService extends CommonService<OutputItem, Long>  {

    void deleteById(Long id);
    Page<OutputItem> getAllByNameContains(String name,Pageable pageable);
    List<OutputItem> getAllItems();
    double getTotalPrice();

    List<OutputItem> getItemsCreatedAfter(LocalDateTime fromDate);

    List<OutputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
