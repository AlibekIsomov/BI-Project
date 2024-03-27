package com.bim.inventory.service;

import com.bim.inventory.dto.SaleStoreDTO;
import com.bim.inventory.entity.SaleStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SaleStoreService {
    Page<SaleStore> getAll(Pageable pageable) throws Exception;

    Optional<SaleStore> getById(Long id) throws Exception;

    Optional<SaleStore> create(SaleStoreDTO data) throws Exception;

    Optional<SaleStore> update(Long id, SaleStoreDTO data) throws Exception;

    List<SaleStore> getAllSaleStoresByStoreId(Long storeId);

    void deleteById(Long id);

    List<SaleStore> findItemsWithinDateRange(Instant startDate, Instant endDate);
}
