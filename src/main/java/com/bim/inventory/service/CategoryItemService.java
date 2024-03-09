package com.bim.inventory.service;

import com.bim.inventory.dto.CategoryItemDTO;
import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.CategoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryItemService {
    Page<CategoryItem> getAll(Pageable pageable) throws Exception;

    Optional<CategoryItem> getById(Long id) throws Exception;

    Optional<CategoryItem> create(CategoryItemDTO data) throws Exception;

    Optional<CategoryItem> update(Long id, CategoryItemDTO data) throws Exception;

    Page<CategoryItem> getAllByNameContains(String name, Pageable pageable);

    void deleteById(Long id);
}
