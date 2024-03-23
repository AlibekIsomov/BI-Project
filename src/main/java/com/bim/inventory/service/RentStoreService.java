package com.bim.inventory.service;

import com.bim.inventory.dto.RentStoreDTO;
import com.bim.inventory.entity.RentStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RentStoreService {
    Page<RentStore> getAll(Pageable pageable) throws Exception;

    Optional<RentStore> getById(Long id) throws Exception;

    Optional<RentStore> create(RentStoreDTO data) throws Exception;

    Optional<RentStore> update(Long id, RentStoreDTO data) throws Exception;

    void deleteById(Long id);

    List<RentStore> findItemsWithinDateRange(Instant startDate, Instant endDate);
}
