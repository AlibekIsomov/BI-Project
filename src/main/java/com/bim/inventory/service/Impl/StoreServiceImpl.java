package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.Store;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.repository.StoreRepository;
import com.bim.inventory.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    PaymentRepository paymentRepository;



    @Override
    public Page<Store> getAll(Pageable pageable) throws Exception {
        return storeRepository.findAll(pageable);
    }

    @Override
    public Optional<Store> getById(Long id) throws Exception {
        if (!storeRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return storeRepository.findById(id);
    }


    @Override
    public Optional<Store> create(StoreDTO data) throws Exception {

        Store store = new Store();
        store.setFullAmount(data.getFullAmount());
        store.setContractNumber(data.getContractNumber());
        store.setFullName(data.getFullName());
        store.setStoreNumber(data.getStoreNumber());
        store.setInitialPayment(data.getInitialPayment());
        store.setSize(data.getSize());

        return Optional.of(storeRepository.save(store));
    }

    @Override
    public Optional<Store> update(Long id, StoreDTO data) throws Exception {
        Optional<Store> existingStore = storeRepository.findById(id);

        if (!existingStore.isPresent()) {
            logger.info("Store with id " + id + " does not exist");
            return Optional.empty();
        }


        Store storeToUpdate = existingStore.get();

        storeToUpdate.setFullAmount(data.getFullAmount());
        storeToUpdate.setContractNumber(data.getContractNumber());
        storeToUpdate.setFullName(data.getFullName());
        storeToUpdate.setStoreNumber(data.getStoreNumber());
        storeToUpdate.setInitialPayment(data.getInitialPayment());
        storeToUpdate.setSize(data.getSize());


        return Optional.of(storeRepository.save(storeToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        if(!storeRepository.existsById(id)) {
            logger.info("Store with id " + id + " does not exists");
        }
        storeRepository.deleteById(id);
    }


    @Override
    public Page<Store> getAllByStoreNumberContains(int storeNumber, Pageable pageable) {
        return storeRepository.findAllByStoreNumber(storeNumber, pageable);
    }


    @Override
    public List<Store> findItemsWithinDateRange(Instant startDate, Instant endDate) {
        return storeRepository.findByCreatedAtBetween(startDate, endDate);

    }



//    @Override
//    public List<Store> getByFullAmount(int fullAmount) {
//        return storeRepository.findByFullAmount();
//    }


    private List<Payment> getPaymentsByStore(Store store) {
        return paymentRepository.findByStore(store);
    }

    public ResponseEntity<StoreDTO> updatePayment(Long storeId, double newPayment) {
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
    public double calculateTotalPaymentsByStore(Long storeId) {
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
//        Optional<Store> storeOptional = storeRepository.findByFullAmount();
//
//        double totalPayments = calculateTotalPaymentsByStore(storeId);
//        double remainingAmount = Math.max( - totalPayments, 0);
//
//        return remainingAmount;
//    }


    private PaymentDTO convertToPaymentDTO(Payment payments) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setNewPayment(payments.getNewPayment());
        return paymentDTO;
    }


}
