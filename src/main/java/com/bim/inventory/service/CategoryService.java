package com.bim.inventory.service;

import com.bim.inventory.entity.Category;


public interface CategoryService extends CommonService<Category, Long> {
    public boolean delete(Long id);

}
