package com.bim.inventory.service;

import com.bim.inventory.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService extends CommonService<Inventory , Long>{
    Page<Inventory> getAllByNameContains(String name, Pageable pageable);
}
