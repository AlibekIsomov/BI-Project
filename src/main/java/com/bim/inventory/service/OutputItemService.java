package com.bim.inventory.service;

import com.bim.inventory.dto.InputDTO;
import com.bim.inventory.dto.OutputDTO;
import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OutputItemService extends CommonService<OutputItem, Long> {
    List<OutputItem> getAllItems();
    double getTotalPrice();

    List<OutputItem> getItemsCreatedAfter(LocalDateTime fromDate);

    List<OutputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);

    public Page<OutputDTO> getAllDTO(Pageable pageable);
}
