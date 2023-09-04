package com.bim.inventory.service;

import com.bim.inventory.entity.InputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InputItemService extends CommonService <InputItem, Long>  {
    Page<InputItem> getAll(Pageable pageable) throws Exception;
    Optional<InputItem> getById(Long id) throws Exception;
    Optional<InputItem> create(InputItem data) throws Exception;
    Optional<InputItem> update(InputItem data) throws Exception;
    void deleteById(Long id);

    Page<InputItem> getAllByNameContains(String name,Pageable pageable);
    List<InputItem> getAllItems();
    double getTotalPrice();

    List<InputItem> getItemsCreatedAfter(LocalDateTime fromDate);
    List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);


}

