package com.bim.inventory.repository;


import com.bim.inventory.entity.MonthlySalaryPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlySalaryPaymentRepository extends JpaRepository<MonthlySalaryPayment, Long> {
    List<MonthlySalaryPayment> findAllByMonthlySalaryId(Long id);


    List<MonthlySalaryPayment> findByMonthlySalaryId(Long id);
}