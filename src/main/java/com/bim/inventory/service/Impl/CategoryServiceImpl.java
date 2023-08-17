package com.bim.inventory.service.Impl;


import com.bim.inventory.entity.Category;
import com.bim.inventory.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Page<Category> getAll(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public Optional<Category> getById(Long id) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Category> create(Category data) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Category> update(Category data) throws Exception {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
