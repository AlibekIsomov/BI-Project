package com.bim.inventory.repository;


import com.bim.inventory.entity.RentStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RentStoreRepository extends JpaRepository<RentStore , Long> {


    List<RentStore> findByCreatedAtBetween(Instant startDate, Instant endDate);

    Iterable<? extends RentStore> findAllByStoreId(Long id);

    boolean existsByStoreId(Long storeId);
}
