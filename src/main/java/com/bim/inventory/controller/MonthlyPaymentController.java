package com.bim.inventory.controller;


import com.bim.inventory.dto.MonthlyPaymentDTO;
import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.RentStoreDTO;
import com.bim.inventory.entity.MonthlyPayment;
import com.bim.inventory.entity.RentStore;
import com.bim.inventory.repository.MonthlyPaymentRepository;
import com.bim.inventory.service.MonthlyPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/monthlyPayment")
public class MonthlyPaymentController {


    @Autowired
    MonthlyPaymentService monthlyPaymentService;

    @Autowired
    MonthlyPaymentRepository monthlyPaymentRepository;

    @PostMapping
    public ResponseEntity<List<MonthlyPayment>> createMonthlyPayments(@RequestBody MonthlyPaymentDTO data) {
        try {
            List<MonthlyPayment> createdPayments = monthlyPaymentService.create(data);
            return ResponseEntity.ok(createdPayments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Customize error handling as needed
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<MonthlyPayment> update(@PathVariable Long id, @RequestBody MonthlyPaymentDTO data) {
        try {
            Optional<MonthlyPayment> updatedPayment = monthlyPaymentService.update(id, data);

            if (updatedPayment.isPresent()) {
                return ResponseEntity.ok(updatedPayment.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException storeNotFoundException) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/all/{rentStoreId}")
    public ResponseEntity<List<MonthlyPayment>> getAllPayments(@PathVariable Long rentStoreId) {
        return monthlyPaymentService.getAllPayments(rentStoreId);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId) {
        try {
            monthlyPaymentService.deletePayment(paymentId);
            return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log the error)
            return new ResponseEntity<>("Failed to delete payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
