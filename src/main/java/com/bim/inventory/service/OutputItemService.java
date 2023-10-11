package com.bim.inventory.service;

import com.bim.inventory.dto.OutputDTO;
import com.bim.inventory.entity.OutputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OutputItemService extends CommonService<OutputItem, Long>  {

    Optional<OutputItem> create(OutputDTO data) throws Exception;

    Optional<OutputItem> update(Long id, OutputDTO data) throws Exception;

    void deleteById(Long id);

    Page<OutputItem> getAllByNameContains(String name,Pageable pageable);

    List<OutputItem> getAllItems();

    double getTotalPrice();

    List<OutputItem> findItemsWithinDateRange(Instant startDate, Instant endDate);
}
