package com.bim.inventory.controller;


import com.bim.inventory.dto.MonthlySalaryDTO;
import com.bim.inventory.entity.MonthlySalary;
import com.bim.inventory.repository.MonthlySalaryRepository;
import com.bim.inventory.service.MonthlySalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/monthlySalary")
public class    MonthlySalaryController {
    @Autowired
    MonthlySalaryService monthlySalaryService;
    @Autowired
    MonthlySalaryRepository monthlySalaryRepository;

    @GetMapping
    public ResponseEntity<Page<MonthlySalary>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(monthlySalaryService.getAll(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<MonthlySalary> getById(@PathVariable Long id) throws Exception {
        return monthlySalaryService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MonthlySalary> create(@RequestBody MonthlySalaryDTO data) throws Exception {
        try {
            Optional<MonthlySalary> createdMonthlySalary = monthlySalaryService.create(data);

            if (createdMonthlySalary.isPresent()) {
                return ResponseEntity.ok(createdMonthlySalary.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlySalary> update(@PathVariable Long id, @RequestBody MonthlySalaryDTO data) {
        try {
            Optional<MonthlySalary> updatedMonthlySalary = monthlySalaryService.update(id, data);

            if (updatedMonthlySalary.isPresent()) {
                return ResponseEntity.ok(updatedMonthlySalary.get());
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
        monthlySalaryService.deleteById(id);
    }

//    @GetMapping("/{workerId}/payments")
//    public ResponseEntity<List<MonthlySalaryDTO>> getAllWorker(@PathVariable Long workerId) {
//        return monthlySalaryService.getAllWorker(workerId);
//    }

    @GetMapping("/worker/{workerId}")
    public List<MonthlySalary> getMonthlySalariesByWorkerId(@PathVariable Long workerId) {
        return monthlySalaryService.getMonthlySalariesByWorkerId(workerId);
    }
}
