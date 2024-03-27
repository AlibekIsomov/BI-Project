package com.bim.inventory.controller;

import com.bim.inventory.dto.SaleStoreDTO;
import com.bim.inventory.entity.SaleStore;
import com.bim.inventory.repository.SaleStoreRepository;
import com.bim.inventory.service.SaleStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/saleStore")
public class SaleStoreController {

    @Autowired
    SaleStoreService saleStoreService;
    @Autowired
    SaleStoreRepository storeRepository;

    @Transactional
    @GetMapping
    public ResponseEntity<Page<SaleStore>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(saleStoreService.getAll(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<SaleStore> getById(@PathVariable Long id) throws Exception {
        return saleStoreService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SaleStore> create(@RequestBody SaleStoreDTO data) throws Exception {
        try {
            Optional<SaleStore> createdSaleStore = saleStoreService.create(data);

            if (createdSaleStore.isPresent()) {
                return ResponseEntity.ok(createdSaleStore.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/by-store/{storeId}")
    public ResponseEntity<List<SaleStore>> getAllSaleStoresByStoreId(@PathVariable Long storeId) {
        List<SaleStore> saleStores = saleStoreService.getAllSaleStoresByStoreId(storeId);
        return ResponseEntity.ok(saleStores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleStore> update(@PathVariable Long id, @RequestBody SaleStoreDTO data) {
        try {
            Optional<SaleStore> updatedStore = saleStoreService.update(id, data);

            if (updatedStore.isPresent()) {
                return ResponseEntity.ok(updatedStore.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException storeNotFoundException) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        saleStoreService.deleteById(id);
    }


}
