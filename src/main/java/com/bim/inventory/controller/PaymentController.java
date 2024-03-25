package com.bim.inventory.controller;


import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/total/{saleStoreId}")
        public ResponseEntity<Double> getTotalPaymentsByStore(@PathVariable Long saleStoreId) {
        double totalPayments = paymentService.calculateTotalPaymentsByStore(saleStoreId);
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


    @PostMapping("/{saleStoreId}/add-payment")
    public ResponseEntity<Payment> addPayment(
            @PathVariable Long saleStoreId,
            @RequestParam Long newPayment) {
        return paymentService.addPayment(saleStoreId, newPayment);
    }

    @PutMapping("/{saleStoreId}/updatePayment/{paymentId}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long saleStoreId,
            @PathVariable Long paymentId,
            @RequestParam Long newPayment
    ) {
        return paymentService.updatePayment(saleStoreId, paymentId, newPayment);
    }

    @GetMapping("/{saleStoreId}/payments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments(@PathVariable Long saleStoreId) {
        return paymentService.getAllPayments(saleStoreId);
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