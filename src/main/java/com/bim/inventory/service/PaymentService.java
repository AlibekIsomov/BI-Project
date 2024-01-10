package com.bim.inventory.service;

import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.Store;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface PaymentService {


    ResponseEntity<Payment> addPayment(Long storeId, double newPayment);

    ResponseEntity<Payment> updatePayment(Long storeId, Long paymentId, double newPayment);

    double calculateTotalPaymentsByStore(Long storeId);

    StoreDTO convertToDTO(Store store);

//    double releasePaidAmount(Long storeId, int fullAmount);


    void deletePayment(Long paymentId);

    ResponseEntity<List<PaymentDTO>> getAllPayments(Long storeId);
}
