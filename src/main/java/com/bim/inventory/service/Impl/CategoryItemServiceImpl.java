package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.CategoryItemDTO;
import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.CategoryItem;
import com.bim.inventory.repository.CategoryItemRepository;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.CategoryItemService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryItemServiceImpl implements CategoryItemService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryItemServiceImpl.class);

    @Autowired
    InputItemRepository inputItemRepository;

    @Autowired
    OutputItemRepository outputItemRepository;

    @Autowired
    CategoryItemRepository categoryItemRepository;

    @Override
    public Page<CategoryItem> getAll(Pageable pageable) throws Exception {
        return categoryItemRepository.findAll(pageable);
    }

    @Override
    public Optional<CategoryItem> getById(Long id) throws Exception {
        if (!categoryItemRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return categoryItemRepository.findById(id);
    }


    @Override
    public Optional<CategoryItem> create(CategoryItemDTO data) throws Exception {

        CategoryItem categoryItem = new CategoryItem();
        categoryItem.setName(data.getName());

        return Optional.of(categoryItemRepository.save(categoryItem));
    }

    @Override
    public Optional<CategoryItem> update(Long id, CategoryItemDTO data) throws Exception {
        Optional<CategoryItem> optionalCategoryItem = categoryItemRepository.findById(id);

        if (optionalCategoryItem.isPresent()) {
            CategoryItem categoryItem = optionalCategoryItem.get();
            categoryItem.setName(data.getName());


            return Optional.of(categoryItemRepository.save(categoryItem));
        } else {

            throw new NotFoundException("Category not found with ID: " + id);
        }
    }


    @Override
    public Page<CategoryItem> getAllByNameContains(String name, Pageable pageable) {
        return categoryItemRepository.findAllByNameContains(name,pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!categoryItemRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
        }
        outputItemRepository.deleteAll(outputItemRepository.findAllByCategoryItemId(id));
        inputItemRepository.deleteAll(inputItemRepository.findAllByCategoryItemId(id));

        categoryItemRepository.deleteById(id);
    }
}
