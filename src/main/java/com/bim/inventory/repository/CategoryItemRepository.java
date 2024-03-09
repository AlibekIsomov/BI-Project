package com.bim.inventory.repository;


import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.CategoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
    Page<CategoryItem> findAllByNameContains(String name, Pageable pageable);
}
