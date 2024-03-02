package com.bim.inventory.controller;


import com.bim.inventory.dto.MonthlySalaryDTO;
import com.bim.inventory.dto.MonthlySalaryPaymentDTO;
import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.entity.MonthlySalary;
import com.bim.inventory.entity.MonthlySalaryPayment;
import com.bim.inventory.repository.MonthlySalaryPaymentRepository;
import com.bim.inventory.repository.MonthlySalaryRepository;
import com.bim.inventory.service.MonthlySalaryPaymentsSevice;
import com.bim.inventory.service.MonthlySalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/monthlySalaryPayment")
public class  MonthlySalaryPaymentController {

    @Autowired
    MonthlySalaryPaymentsSevice monthlySalaryPaymentsSevice;
    @Autowired
    MonthlySalaryPaymentRepository monthlySalaryPaymentRepository;

    @PostMapping
    public ResponseEntity<MonthlySalaryPayment> create(@RequestBody MonthlySalaryPaymentDTO data) throws Exception {
        try {
            Optional<MonthlySalaryPayment> createdMonthlySalary = monthlySalaryPaymentsSevice.create(data);

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
    public ResponseEntity<MonthlySalaryPayment> update(@PathVariable Long id, @RequestBody MonthlySalaryPaymentDTO data) {
        try {
            Optional<MonthlySalaryPayment> updatedMonthlySalary = monthlySalaryPaymentsSevice.update(id, data);

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
        monthlySalaryPaymentsSevice.deletePayment(id);
    }
    @GetMapping("/get/{monthlySalaryId}")
    public List<MonthlySalaryPayment> getMonthlySalariesByMonthlySalaryId(@PathVariable Long monthlySalaryId) {
        return monthlySalaryPaymentsSevice.getMonthlySalariesByMonthlySalaryId(monthlySalaryId);
    }
}
