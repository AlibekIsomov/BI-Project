package com.bim.inventory.controller;


import com.bim.inventory.dto.RentStoreDTO;
import com.bim.inventory.entity.MonthlyPayment;
import com.bim.inventory.entity.RentStore;
import com.bim.inventory.repository.RentStoreRepository;
import com.bim.inventory.service.MonthlyPaymentService;
import com.bim.inventory.service.RentStoreService;
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
@RequestMapping("/api/rentStore")
public class RentStoreController {

    @Autowired
    RentStoreService rentStoreService;
    @Autowired
    RentStoreRepository rentStoreRepository;
    @Autowired
    MonthlyPaymentService monthlyPaymentService;

    @Transactional
    @GetMapping
    public ResponseEntity<Page<RentStore>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(rentStoreService.getAll(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<RentStore> getById(@PathVariable Long id) throws Exception {
        return rentStoreService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RentStore> create(@RequestBody RentStoreDTO data) throws Exception {
        try {
            Optional<RentStore> createdRentStore = rentStoreService.create(data);
//            List<MonthlyPayment> createdPayments = monthlyPaymentService.create(data);
            if (createdRentStore.isPresent()) {
                return ResponseEntity.ok(createdRentStore.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/by-store/{storeId}")
    public ResponseEntity<List<RentStore>> getAllRentStoresByStoreId(@PathVariable Long storeId) {
        List<RentStore> rentStores = rentStoreService.getAllRentStoresByStoreId(storeId);
        return ResponseEntity.ok(rentStores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentStore> update(@PathVariable Long id, @RequestBody RentStoreDTO data) {
        try {
            Optional<RentStore> updatedStore = rentStoreService.update(id, data);

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
        rentStoreService.deleteById(id);
    }
}
