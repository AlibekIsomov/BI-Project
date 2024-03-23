package com.bim.inventory.service;

import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.SaleStoreDTO;
import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.SaleStore;
import com.bim.inventory.entity.Store;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface PaymentService {


    ResponseEntity<Payment> addPayment(Long storeId, Long newPayment);

    ResponseEntity<Payment> updatePayment(Long storeId, Long paymentId, Long newPayment);

    double calculateTotalPaymentsByStore(Long saleStoreId);

    SaleStoreDTO convertToDTO(SaleStore store);

//    double releasePaidAmount(Long storeId, int fullAmount);


    void deletePayment(Long paymentId);

    ResponseEntity<List<PaymentDTO>> getAllPayments(Long saleStoreId);
}
