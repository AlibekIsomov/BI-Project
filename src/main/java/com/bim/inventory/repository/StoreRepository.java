package com.bim.inventory.repository;


import com.bim.inventory.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findAllByStoreNumber(int storeNumber, Pageable pageable);

    List<Store> findByCreatedAtBetween(Instant startDate, Instant endDate);

//    Optional<Store> findByFullAmount();

//    @Query("SELECT COALESCE(SUM(s.initialPayment) + SUM(s.newPayment), 0) FROM Store s WHERE s = :store")
//    BigDecimal calculateTotalPaymentsByStore(@Param("store") Store store);


}
