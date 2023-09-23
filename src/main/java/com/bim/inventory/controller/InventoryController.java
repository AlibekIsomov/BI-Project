package com.bim.inventory.controller;

import com.bim.inventory.dto.InventoryDTO;
import com.bim.inventory.entity.Inventory;
import com.bim.inventory.repository.InventoryRepository;
import com.bim.inventory.service.InventoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    InventoryService inventoryService;


    @GetMapping
    public ResponseEntity<Page<Inventory>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(inventoryService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getById(@PathVariable Long id) throws Exception {
        return inventoryService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    public ResponseEntity<Inventory> create(@RequestBody InventoryDTO data) {
        try {
            Optional<Inventory> inventory = inventoryService.create(data);

            if(inventory.isPresent()){
                return ResponseEntity.ok(inventory.get());
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> update(@PathVariable Long id,
                                             @RequestBody InventoryDTO data) {
        try {
            Optional<Inventory> updatedInventory = inventoryService.update(id, data);

            if (updatedInventory.isPresent()) {
                return ResponseEntity.ok(updatedInventory.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException categoryNotFoundException) {
            // Handle category not found exception, e.g., return a bad request response
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Handle other exceptions, e.g., log the error and return an internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        inventoryService.deleteById(id);
    }

    @GetMapping("/search-name/{name}")
    public ResponseEntity<Page<Inventory>> searchName(@PathVariable String name, Pageable pageble) {
        return ResponseEntity.ok(inventoryService.getAllByNameContains(name,pageble));
    }
}
