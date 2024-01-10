package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.Store;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.repository.StoreRepository;
import com.bim.inventory.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    StoreRepository storeRepository;


    @Override
    public ResponseEntity<StoreDTO> addPayment(Long storeId, double newPayment) {
        Optional<Store> storeOptional = storeRepository.findById(storeId);

        if (storeOptional.isPresent()) {
            Store store = storeOptional.get();

            // Create a new payment record for the new payment
            Payment payment = new Payment();
            payment.setNewPayment(newPayment);
            payment.setStore(store);

            // Add the new payment to the store's list of payments
            store.getPayments().add(payment);

            // Update the store's lastPayment to the new payment
            store.setLastPayment(newPayment);

            // Save the updated store (including the new payment)
            storeRepository.save(store);

            // Convert and return the updated store as a DTO
            StoreDTO updatedStoreDTO = convertToDTO(store);
            return ResponseEntity.ok(updatedStoreDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<StoreDTO> updatePayment(Long storeId, Long paymentId, double newPayment) {
        Optional<Store> storeOptional = storeRepository.findById(storeId);

        if (storeOptional.isPresent()) {
            Store store = storeOptional.get();

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
                StoreDTO updatedStoreDTO = convertToDTO(store);
                return ResponseEntity.ok(updatedStoreDTO);
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
    public double calculateTotalPaymentsByStore(Long storeId){
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + storeId));

        return paymentRepository.calculateTotalPaymentsByStore(store);
    }


    @Override
    public StoreDTO convertToDTO(Store store) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setFullAmount(store.getFullAmount());
        storeDTO.setContractNumber(store.getContractNumber());
        storeDTO.setFullName(store.getFullName());
        storeDTO.setSize(store.getSize());
        storeDTO.setStoreNumber(store.getStoreNumber());

        List<PaymentDTO> paymentDTOs = store.getPayments()
                .stream()
                .map(this::convertToPaymentDTO)
                .collect(Collectors.toList());
        storeDTO.setPayments(paymentDTOs);

        return storeDTO;
    }

//    @Override
//    public double releasePaidAmount(Long storeId) {
//
//        double totalPayments = calculateTotalPaymentsByStore(storeId);
//      double remainingAmount = Math.max( - totalPayments, 0);
//
//        return remainingAmount;
//    }


    @Override
    public void deletePayment(Long paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

        if (paymentOptional.isPresent()) {
            Payment paymentToDelete = paymentOptional.get();

            // Remove the payment from the associated store's payments list
            Store store = paymentToDelete.getStore();
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
            Optional<Store> storeOptional = storeRepository.findById(storeId);

            if (storeOptional.isPresent()) {
                Store store = storeOptional.get();

                List<PaymentDTO> paymentDTOs = store.getPayments()
                        .stream()
                        .map(this::convertToPaymentDTO)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(paymentDTOs);
            } else {
                return ResponseEntity.notFound().build();
            }
        }



        private PaymentDTO convertToPaymentDTO(Payment payments) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payments.getId());
        paymentDTO.setNewPayment(payments.getNewPayment());
        return paymentDTO;

    }



}
