package com.bim.inventory.controller;


import com.bim.inventory.dto.CategoryItemDTO;
import com.bim.inventory.entity.CategoryItem;
import com.bim.inventory.repository.CategoryItemRepository;
import com.bim.inventory.service.CategoryItemService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categoryItem")
public class CategoryItemController {
    @Autowired
    CategoryItemRepository categoryItemRepositoryRepository;
    @Autowired
    CategoryItemService categoryItemService;

    @GetMapping
    public ResponseEntity<Page<CategoryItem>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(categoryItemService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryItem> getById(@PathVariable Long id) throws Exception {
        return categoryItemService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryItem> create(@RequestBody CategoryItemDTO data) throws Exception {
        try {
            Optional<CategoryItem> createdCategory = categoryItemService.create(data);

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
    public ResponseEntity<CategoryItem> update(@PathVariable Long id,
                                           @RequestBody CategoryItemDTO data) throws Exception {
        try {
            Optional<CategoryItem> createdCategory = categoryItemService.update(id, data);

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
        categoryItemService.deleteById(id);
    }

    @GetMapping("/search-name/{name}")
    public ResponseEntity<Page<CategoryItem>> searchName(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(categoryItemService.getAllByNameContains(name,pageable));
    }
}

