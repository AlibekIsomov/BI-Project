package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.SaleStoreDTO;
import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.SaleStore;
import com.bim.inventory.entity.Store;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.repository.SaleStoreRepository;
import com.bim.inventory.repository.StoreRepository;
import com.bim.inventory.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    SaleStoreRepository storeRepository;


    @Override
    public ResponseEntity<Payment> addPayment(Long storeId, Long newPayment) {
        Optional<SaleStore> storeOptional = storeRepository.findById(storeId);

        if (storeOptional.isPresent()) {
            SaleStore store = storeOptional.get();

            // Check if the new payment is greater than or equal to the full amount
            if (newPayment > store.getFullAmount() || calculateTotalPaymentsByStore(storeId) >= store.getFullAmount()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            // Create a new payment record for the new payment
            Payment payment = new Payment();
            payment.setNewPayment(newPayment);
            payment.setSaleStore(store);

            // Add the new payment to the store's list of payments
            store.getPayments().add(payment);

            // Update the store's lastPayment to the new payment
            store.setLastPayment(newPayment);

            // Save the updated store (including the new payment)
            storeRepository.save(store);

            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<Payment> updatePayment(Long storeId, Long paymentId, Long newPayment) {
        Optional<SaleStore> storeOptional = storeRepository.findById(storeId);

        if (storeOptional.isPresent()) {
            SaleStore store = storeOptional.get();

            // Find the existing payment by ID
            Optional<Payment> paymentOptional = store.getPayments().stream()
                    .filter(payment -> payment.getId().equals(paymentId))
                    .findFirst();

            if (paymentOptional.isPresent()) {
                Payment existingPayment = paymentOptional.get();

                // Update the existing payment
                existingPayment.setNewPayment(newPayment);

                // Update the store's lastPayment to the new payment
                store.setLastPayment(newPayment);

                // Save the updated store (including the updated payment)
                storeRepository.save(store);

                // Convert and return the updated store as a DTO
                return ResponseEntity.ok(existingPayment);
            } else {
                // Handle the case where the specified payment ID is not found
                return ResponseEntity.notFound().build();
            }
        } else {
            // Handle the case where the specified store ID is not found
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public double calculateTotalPaymentsByStore(Long saleStoreId){
        SaleStore saleStore = storeRepository.findById(saleStoreId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + saleStoreId));

        return paymentRepository.calculateTotalPaymentsBySaleStore(saleStore);
    }





    @Override
    public void deletePayment(Long paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

        if (paymentOptional.isPresent()) {
            Payment paymentToDelete = paymentOptional.get();

            // Remove the payment from the associated store's payments list
            SaleStore store = paymentToDelete.getSaleStore();
            if (store != null) {
                store.getPayments().remove(paymentToDelete);
            }

            // Delete the payment from the database
            paymentRepository.delete(paymentToDelete);
        } else {
            // Handle the case where the payment with the given id is not found
            // You can throw an exception, log a message, or handle it in another way.
            // For simplicity, I'll log a message.
            System.out.println("Payment with id " + paymentId + " not found");
        }
    }

        @Override
        public ResponseEntity<List<PaymentDTO>> getAllPayments (Long storeId){
            Optional<SaleStore> storeOptional = storeRepository.findById(storeId);

            if (storeOptional.isPresent()) {
                SaleStore store = storeOptional.get();

                List<PaymentDTO> paymentDTOs = store.getPayments()
                        .stream()
                        .map(this::convertToPaymentDTO)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(paymentDTOs);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    @Override
    public SaleStoreDTO convertToDTO(SaleStore saleStore) {
        SaleStoreDTO saleStoreDTO = new SaleStoreDTO();
        saleStoreDTO.setId(saleStore.getId());
        saleStoreDTO.setFullAmount(saleStore.getFullAmount());

        List<PaymentDTO> paymentDTOs = saleStore.getPayments()
                .stream()
                .map(this::convertToPaymentDTO)
                .collect(Collectors.toList());
        saleStoreDTO.setPayments(paymentDTOs);

        return saleStoreDTO;
    }


        private PaymentDTO convertToPaymentDTO(Payment payments) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payments.getId());
        paymentDTO.setNewPayment(payments.getNewPayment());
        paymentDTO.setCreatedAt(payments.getCreatedAt());
        return paymentDTO;

    }



}
