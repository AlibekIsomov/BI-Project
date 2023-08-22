package com.bim.inventory.service;

import com.bim.inventory.dto.InputDTO;
import com.bim.inventory.dto.OutputDTO;
import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OutputItemService  {

    Page<OutputDTO> getAll(Pageable pageable) throws Exception;
    Optional<OutputDTO> getById(Long id) throws Exception;
    Optional<OutputDTO> create(OutputItem data) throws Exception;
    Optional<OutputDTO> update(OutputItem data) throws Exception;
    void deleteById(Long id);
    Page<OutputItem> getAllByNameContains(String name,Pageable pageable);
    List<OutputItem> getAllItems();
    double getTotalPrice();

    List<OutputItem> getItemsCreatedAfter(LocalDateTime fromDate);

    List<OutputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);

    public Page<OutputDTO> getAllDTO(Pageable pageable);
}
