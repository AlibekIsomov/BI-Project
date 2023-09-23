package com.bim.inventory.repository;


import com.bim.inventory.entity.InputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface InputItemRepository extends JpaRepository<InputItem, Long> {

    Page<InputItem> findAllByNameContains(String name, Pageable pageable);

    public Page<InputItem> findAllByOrderByIdDesc(Pageable pageable);

    List<InputItem> findByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<InputItem> findByCreatedAtAfter(LocalDateTime fromDate);

    List<InputItem> findByCreatedAtBetween(Date startDate, Date endDate);

}
