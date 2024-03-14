package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.CategoryDTO;
import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.FileEntity;
import com.bim.inventory.repository.*;
import com.bim.inventory.service.CategoryService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    FileRepository fileRepository;

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
        category.setLocationName(data.getLocationName());

        return Optional.of(categoryRepository.save(category));
    }
    @Override
    public Optional<Category> update(Long id, CategoryDTO data) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            List<FileEntity> oldFileEntities = category.getFileEntity();

            // Check if file entity IDs are provided and not empty
            if (data.getFileEntityIds() != null && !data.getFileEntityIds().isEmpty()) {
                List<FileEntity> newFileEntities = new ArrayList<>();
                for (Long fileId : data.getFileEntityIds()) {
                    Optional<FileEntity> newFileEntityOptional = fileRepository.findById(fileId);

                    if (!newFileEntityOptional.isPresent()) {
                        throw new NotFoundException("FileEntity with id " + fileId + " does not exist");
                    }

                    FileEntity newFileEntity = newFileEntityOptional.get();
                    newFileEntities.add(newFileEntity);

                }

                    // Set new file entities
                    category.getFileEntity().addAll(newFileEntities);
                } else{
                    // If no file entities are provided, clear the existing ones
                    fileRepository.deleteAll(oldFileEntities);

            }

            // Update category name
            category.setLocationName(data.getLocationName());

            // Save the updated category
            return Optional.of(categoryRepository.save(category));
        } else {
            // Handle the case where the category with the given ID doesn't exist
            throw new NotFoundException("Category not found with ID: " + id);
        }
    }

    @Override
    public Page<Category> getAllByNameContains(String name, Pageable pageable) {
        return categoryRepository.findAllByLocationName(name,pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!categoryRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
        }
        storeRepository.deleteAll(storeRepository.findAllByCategoryId(id));

        categoryRepository.deleteById(id);
    }


    @Override
    public void deleteFileEntity(Long categoryId, Long fileEntityId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<FileEntity> fileEntities = category.getFileEntity();

            // Find the FileEntity to delete
            Optional<FileEntity> fileEntityOptional = fileEntities.stream()
                    .filter(fileEntity -> fileEntity.getId().equals(fileEntityId))
                    .findFirst();

            // If found, remove it from the list
            fileEntityOptional.ifPresent(fileEntity -> {
                fileEntities.remove(fileEntity);
                categoryRepository.save(category); // Update the category entity in the database
            });
        } else {
            logger.info(id + " does not exists");
        }
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

}
