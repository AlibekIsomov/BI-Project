package com.bim.inventory.controller;


import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.SalaryChange;
import com.bim.inventory.entity.Worker;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.service.PaymentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;


    @GetMapping("/total/{storeId}")
    public ResponseEntity<Double> getTotalPaymentsByStore(@PathVariable Long storeId) {
        double totalPayments = paymentService.calculateTotalPaymentsByStore(storeId);
        return ResponseEntity.ok(totalPayments);
    }

//    @GetMapping("/release/{storeId}")
//    public ResponseEntity<Double> releasePaidAmount(
//            @PathVariable Long storeId,
//            @RequestParam int fullAmount)
//    {
//        double remainingAmount = paymentService.releasePaidAmount(storeId,  fullAmount );
//        return ResponseEntity.ok(remainingAmount);
//    }


    @PutMapping("/{storeId}/add-payment")
    public ResponseEntity<StoreDTO> addPayment(
            @PathVariable Long storeId,
            @RequestParam double newSalary) {
        return paymentService.addPayment(storeId, newSalary);
    }

    @PutMapping("/{storeId}/updatePayment/{paymentId}")
    public ResponseEntity<StoreDTO> updatePayment(
            @PathVariable Long storeId,
            @PathVariable Long paymentId,
            @RequestParam double newPayment
    ) {
        return paymentService.updatePayment(storeId, paymentId, newPayment);
    }

    @GetMapping("/{storeId}/payments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments(@PathVariable Long storeId) {
        return paymentService.getAllPayments(storeId);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId) {
        try {
            paymentService.deletePayment(paymentId);
            return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log the error)
            return new ResponseEntity<>("Failed to delete payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}