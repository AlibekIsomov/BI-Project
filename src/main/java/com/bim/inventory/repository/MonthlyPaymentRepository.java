package com.bim.inventory.repository;

import com.bim.inventory.entity.MonthlyPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MonthlyPaymentRepository extends JpaRepository<MonthlyPayment , Long> {
    List<MonthlyPayment> findByRentStoreId(Long rentStoreId);
}
