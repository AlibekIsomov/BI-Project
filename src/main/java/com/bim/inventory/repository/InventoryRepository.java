package com.bim.inventory.repository;

import com.bim.inventory.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByCreatedAtBetween(Instant startDate, Instant endDate);
    Page<Inventory> findAllByNameContains(String name, Pageable pageable);
}
