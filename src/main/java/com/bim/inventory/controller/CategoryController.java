package com.bim.inventory.controller;


import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.OutputItem;
import com.bim.inventory.repository.CategoryRepository;
import com.bim.inventory.service.CategoryService;
import com.bim.inventory.service.Impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@Controller
public class CategoryController implements CommonController<Category,Long> {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @Override
    @GetMapping
    public ResponseEntity<Page<Category>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) throws Exception {
        return categoryService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category data) throws Exception {
        return categoryService.create(data).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping
    public ResponseEntity<Category> update(@RequestBody Category data) throws Exception {
        return categoryService.update(data).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

}
