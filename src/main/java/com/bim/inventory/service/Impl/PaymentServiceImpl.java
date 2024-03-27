package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.SaleStoreDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.SaleStore;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.repository.SaleStoreRepository;
import com.bim.inventory.service.PaymentService;
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
    public ResponseEntity<Payment> addPayment(Long saleStoreId, Long newPayment) {
        Optional<SaleStore> storeOptional = storeRepository.findById(saleStoreId);

        if (storeOptional.isPresent()) {
            SaleStore saleStore = storeOptional.get();

            // Check if the new payment is greater than or equal to the full amount
            if (newPayment > saleStore.getFullAmount() || calculateTotalPaymentsByStore(saleStoreId) >= saleStore.getFullAmount()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            // Create a new payment record for the new payment
            Payment payment = new Payment();
            payment.setNewPayment(newPayment);
            payment.setSaleStore(saleStore);

            // Add the new payment to the store's list of payments
            saleStore.getPayments().add(payment);

            // Update the store's lastPayment to the new payment
            saleStore.setLastPayment(newPayment);

            // Save the updated store (including the new payment)
            storeRepository.save(saleStore);

            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<Payment> updatePayment(Long saleStoreId, Long paymentId, Long newPayment) {
        Optional<SaleStore> storeOptional = storeRepository.findById(saleStoreId);

        if (storeOptional.isPresent()) {
            SaleStore saleStore = storeOptional.get();

            // Find the existing payment by ID
            Optional<Payment> paymentOptional = saleStore.getPayments().stream()
                    .filter(payment -> payment.getId().equals(paymentId))
                    .findFirst();

            if (paymentOptional.isPresent()) {
                Payment existingPayment = paymentOptional.get();

                // Update the existing payment
                existingPayment.setNewPayment(newPayment);

                // Update the store's lastPayment to the new payment
                saleStore.setLastPayment(newPayment);

                // Save the updated store (including the updated payment)
                storeRepository.save(saleStore);

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
        return paymentRepository.calculateTotalPaymentsByStore(saleStore);
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
            System.out.println("Payment with id " + paymentId + " not found");
        }
    }

        @Override
        public ResponseEntity<List<PaymentDTO>> getAllPayments (Long saleStoreId){
            Optional<SaleStore> storeOptional = storeRepository.findById(saleStoreId);

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
