package com.bim.inventory.service;

import com.bim.inventory.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategoryService extends CommonService<Category, Long> {
    Page<Category> getAllByNameContains(String name, Pageable pageable);

}
