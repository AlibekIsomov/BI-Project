package com.bim.inventory.repository;

import com.bim.inventory.entity.OutputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OutputItemRepository extends JpaRepository<OutputItem, Long> {

    Page<OutputItem> findAllByNameContains(String name, Pageable pageable);

    List<OutputItem> findByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<OutputItem> findAllByCategoryItemId(Long id);

    List<OutputItem> findByCreatedAtBetween(Instant startDate, Instant endDate);

}
