package com.bim.inventory.repository;


import com.bim.inventory.entity.SaleStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SaleStoreRepository extends JpaRepository<SaleStore, Long> {
//    Page<SaleStore> findAllByStoreNumber(int storeNumber, Pageable pageable);

    List<SaleStore> findByCreatedAtBetween(Instant startDate, Instant endDate);

    Iterable<? extends SaleStore> findAllByStoreId(Long id);

    boolean existsByStoreId(Long storeId);

//    Optional<Store> findByFullAmount();

//    @Query("SELECT COALESCE(SUM(s.initialPayment) + SUM(s.newPayment), 0) FROM Store s WHERE s = :store")
//    BigDecimal calculateTotalPaymentsByStore(@Param("store") Store store);


}
