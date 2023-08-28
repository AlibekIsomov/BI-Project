package com.bim.inventory.controller;


import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.Inventory;
import com.bim.inventory.repository.CategoryRepository;
import com.bim.inventory.repository.InventoryRepository;
import com.bim.inventory.service.CategoryService;
import com.bim.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@Controller
public class InventoryController implements CommonController<Inventory, Long> {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    InventoryService inventoryService;

    @Override
    @GetMapping
    public ResponseEntity<Page<Inventory>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(inventoryService.getAll(pageable));
    }
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getById(@PathVariable Long id) throws Exception {
        return inventoryService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<Inventory> create(@RequestBody Inventory data) throws Exception {
        return inventoryService.create(data).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping
    public ResponseEntity<Inventory> update(@RequestBody Inventory data) throws Exception {
        return inventoryService.update(data).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        inventoryService.deleteById(id);
    }

    @GetMapping("/search-name/{name}")
    public ResponseEntity<Page<Inventory>> searchName(@PathVariable String name, Pageable pageble) {
        return ResponseEntity.ok(inventoryService.getAllByNameContains(name,pageble));
    }
}
