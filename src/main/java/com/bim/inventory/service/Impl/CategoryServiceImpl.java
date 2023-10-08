package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.CategoryDTO;
import com.bim.inventory.entity.Category;
import com.bim.inventory.repository.CategoryRepository;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.CategoryService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    InputItemRepository inputItemRepository;

    @Autowired
    OutputItemRepository outputItemRepository;

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
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(data.getName());

            // Save the updated category
            return Optional.of(categoryRepository.save(category));
        } else {
            // Handle the case where the category with the given ID doesn't exist
            throw new NotFoundException("Category not found with ID: " + id);
        }
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
        outputItemRepository.deleteAll(outputItemRepository.findAllByCategoryId(id));
        inputItemRepository.deleteAll(inputItemRepository.findAllByCategoryId(id));

        categoryRepository.deleteById(id);
    }

}
