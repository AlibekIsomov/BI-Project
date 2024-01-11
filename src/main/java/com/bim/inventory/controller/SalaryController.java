package com.bim.inventory.controller;


import com.bim.inventory.dto.SalaryDTO;
import com.bim.inventory.entity.Salary;
import com.bim.inventory.repository.SalaryRepository;
import com.bim.inventory.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalaryController {

    @Autowired
    SalaryService salaryService;

    @Autowired
    SalaryRepository salaryRepository;


    @PostMapping("/{workerId}/add-payment")
    public ResponseEntity<Salary> addPayment(
            @PathVariable Long storeId,
            @RequestParam double newPayment) {
        return salaryService.addSalary(storeId, newPayment);
    }

    @PutMapping("/{workerId}/updateSalary/{salaryId}")
    public ResponseEntity<Salary> updatePayment(
            @PathVariable Long workerId,
            @PathVariable Long salaryId,
            @RequestParam double newSalary
    ) {
        return salaryService.updateSalary(workerId, salaryId, newSalary);
    }

    @GetMapping("/{workerId}/salary")
    public ResponseEntity<List<SalaryDTO>> getAllPayments(@PathVariable Long workerId) {
        return salaryService.getAllSalary(workerId);
    }

    @DeleteMapping("/{salaryId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long salaryId) {
        try {
            salaryService.deleteSalary(salaryId);
            return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log the error)
            return new ResponseEntity<>("Failed to delete payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
