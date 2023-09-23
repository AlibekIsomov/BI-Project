package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.CategoryDTO;
import com.bim.inventory.entity.Category;
import com.bim.inventory.repository.CategoryRepository;
import com.bim.inventory.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Page<Category> getAll(Pageable pageable) throws Exception {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> getById(Long id) throws Exception {
        if(!categoryRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return categoryRepository.findById(id);
    }


    @Override
    public Optional<Category> create(CategoryDTO data) throws Exception {

        Category category = new Category();
        category.setName(data.getName());

        return Optional.of(categoryRepository.save(category));
    }
    @Override
    public Optional<Category> update(Long id, CategoryDTO data) throws Exception {

        Category category = new Category();

        category.setName(data.getName());

        return Optional.of(categoryRepository.save(category));
    }
    @Override
    public Page<Category> getAllByNameContains(String name, Pageable pageable) {
        return categoryRepository.findAllByNameContains(name,pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!categoryRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
        }
        categoryRepository.deleteById(id);
    }


}
