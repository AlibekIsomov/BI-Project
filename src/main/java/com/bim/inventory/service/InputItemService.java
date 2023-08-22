package com.bim.inventory.service;

import com.bim.inventory.dto.InputDTO;
import com.bim.inventory.entity.InputItem;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InputItemService   {
    Page<InputDTO> getAll(Pageable pageable) throws Exception;
    Optional<InputDTO> getById(Long id) throws Exception;
    Optional<InputDTO> create(InputItem data) throws Exception;
    Optional<InputDTO> update(InputItem data) throws Exception;
    void deleteById(Long id);

    Page<InputItem> getAllByNameContains(String name,Pageable pageable);
    List<InputItem> getAllItems();
    double getTotalPrice();

    List<InputItem> getItemsCreatedAfter(LocalDateTime fromDate);
    List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);

    public Page<InputDTO> getAllDTO(Pageable pageable);

}

