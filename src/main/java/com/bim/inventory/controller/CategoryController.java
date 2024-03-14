package com.bim.inventory.controller;

import com.bim.inventory.dto.CategoryDTO;
import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.repository.CategoryRepository;
import com.bim.inventory.service.CategoryService;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/category")
public class CategoryController  {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) throws Exception {
        return categoryService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDTO data) throws Exception {
        try {
            Optional<Category> createdCategory = categoryService.create(data);

            if(createdCategory.isPresent()){
                return ResponseEntity.ok(createdCategory.get());
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id,
                                         @RequestBody CategoryDTO data) throws Exception {
        try {
            Optional<Category> createdCategory = categoryService.update(id, data);

            if (createdCategory.isPresent()) {
                return ResponseEntity.ok(createdCategory.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException categoryNotFoundException) {

            return ResponseEntity.badRequest().build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/search-name/{name}")
    public ResponseEntity<Page<Category>> searchName(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllByNameContains(name,pageable));
        }


    @DeleteMapping("/{categoryId}/deleteFile/{fileEntityId}")
    public ResponseEntity<?> deleteFile(
            @PathVariable Long categoryId,
            @PathVariable Long fileEntityId) {
        categoryService.deleteFileEntity(categoryId, fileEntityId);

        return ResponseEntity.ok().body(" ID with " + categoryId + " category's file deleted ");
    }

    }

