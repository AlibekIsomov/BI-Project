package com.bim.inventory.controller;

import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.Store;
import com.bim.inventory.repository.StoreRepository;
import com.bim.inventory.service.StoreService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreRepository storeRepository;

    @Transactional
    @GetMapping
    public ResponseEntity<Page<Store>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(storeService.getAll(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Store> getById(@PathVariable Long id) throws Exception {
        return storeService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Store> create(@RequestBody StoreDTO data) throws Exception {
        try {
            Optional<Store> createdCategory = storeService.create(data);

            return createdCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> update(@PathVariable Long id, @RequestBody StoreDTO data) {
        try {
            Optional<Store> updatedStore = storeService.update(id, data);

            return updatedStore.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException storeNotFoundException) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        storeService.deleteById(id);
    }

    @GetMapping("/search-name/{storeNumber}")
    public ResponseEntity<Page<Store>> searchStoreNumber(@PathVariable int storeNumber, Pageable pageable) {
        return ResponseEntity.ok(storeService.getAllByStoreNumberContains(storeNumber, pageable));
    }

    @GetMapping("/{storeId}/checkConnection")
    public ResponseEntity<String> checkStoreConnection(@PathVariable Long storeId) {
        boolean isConnected = storeService.isStoreConnected(storeId);
        if (isConnected) {
            return ResponseEntity.ok("Store is connected to either SaleStore or RentStore");
        } else {
            return ResponseEntity.ok("Store is not connected to either SaleStore or RentStore");
        }
    }


}
