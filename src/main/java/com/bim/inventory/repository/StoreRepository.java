package com.bim.inventory.repository;


import com.bim.inventory.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findAllByStoreNumber(int storeNumber, Pageable pageable);

    List<Store> findByCreatedAtBetween(Instant startDate, Instant endDate);

//    Optional<Store> findByFullAmount();
}
