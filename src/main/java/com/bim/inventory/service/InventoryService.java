package com.bim.inventory.service;

import com.bim.inventory.dto.InventoryDTO;
import com.bim.inventory.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InventoryService extends CommonService<Inventory , Long>{
    Optional<Inventory> create(InventoryDTO data) throws Exception;

    Page<Inventory> getAllByNameContains(String name, Pageable pageable);

    Optional<Inventory> update(Long id, InventoryDTO data) throws Exception;
}
