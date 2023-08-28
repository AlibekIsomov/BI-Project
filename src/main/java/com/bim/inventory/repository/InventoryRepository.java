package com.bim.inventory.repository;


import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    public Page<Inventory> findAllByOrderByIdDesc(Pageable pageable);

    Page<Inventory> findAllByNameContains(String name, Pageable pageable);
}
