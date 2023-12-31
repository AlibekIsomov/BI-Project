package com.bim.inventory.service;

import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface StoreService {

    Optional<Store> create(StoreDTO data) throws Exception;

    Page<Store> getAll(Pageable pageable) throws Exception;

    Optional<Store> getById(Long id) throws Exception;

    Optional<Store> update(Long id, StoreDTO data) throws Exception;

    void deleteById(Long id);


    Page<Store> getAllByStoreNumberContains(int storeNumber, Pageable pageable);

//    List<Store> getByFullAmount(int fullAmount);

    List<Store> findItemsWithinDateRange(Instant startDate, Instant endDate);

//    double getRemainingAmount(int fullAmount);


    ResponseEntity<StoreDTO> updatePayment(Long storeId, double newPayment);

    double calculateTotalPaymentsByStore(Long storeId);

    StoreDTO convertToDTO(Store store);

//    double releasePaidAmount(Long storeId, double amountToRelease);

//    double releasePaidAmount(Long storeId);
}
