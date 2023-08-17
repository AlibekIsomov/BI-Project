package com.bim.inventory.repository;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OutputItemRepository extends JpaRepository<OutputItem, Long> {
    public Page<OutputItem> findAllByOrderByIdDesc(Pageable pageable);

    List<OutputItem> findByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<OutputItem> findByCreatedAtAfter(LocalDateTime fromDate);
}
