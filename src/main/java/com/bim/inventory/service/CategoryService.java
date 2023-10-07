package com.bim.inventory.service;

import com.bim.inventory.dto.CategoryDTO;
import com.bim.inventory.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface CategoryService extends CommonService<Category, Long> {

    Optional<Category> create(CategoryDTO data) throws Exception;

    Optional<Category> update(Long id, CategoryDTO data) throws Exception;

    Page<Category> getAllByNameContains(String name, Pageable pageable);


    List<Category> getItemsofCategory(Long inputItemId, Long outputItemId);
}
