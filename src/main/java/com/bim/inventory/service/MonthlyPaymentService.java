package com.bim.inventory.service;

import com.bim.inventory.dto.MonthlyPaymentDTO;
import com.bim.inventory.entity.MonthlyPayment;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MonthlyPaymentService {


    List<MonthlyPayment> create(MonthlyPaymentDTO data) throws Exception;

    Optional<MonthlyPayment> update(Long id, MonthlyPaymentDTO data) throws Exception;

    //    @Override
    //    public double calculateTotalPaymentsByStore(Long saleStoreId){
    //        SaleStore saleStore = storeRepository.findById(saleStoreId)
    //                .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + saleStoreId));
    //
    //        return paymentRepository.calculateTotalPaymentsByStore(saleStore);
    //    }
    //
    //
    //
    //
    //
    void deletePayment(Long paymentId);

    ResponseEntity<List<MonthlyPayment>> getAllPayments(Long rentStoreId);
}
