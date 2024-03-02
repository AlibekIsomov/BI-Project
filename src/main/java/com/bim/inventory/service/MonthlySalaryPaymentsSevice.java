package com.bim.inventory.service;

import com.bim.inventory.dto.MonthlySalaryPaymentDTO;
import com.bim.inventory.entity.MonthlySalary;
import com.bim.inventory.entity.MonthlySalaryPayment;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MonthlySalaryPaymentsSevice {
    Optional<MonthlySalaryPayment> create(MonthlySalaryPaymentDTO data) throws Exception;

    Optional<MonthlySalaryPayment> update(Long id, MonthlySalaryPaymentDTO data) throws Exception;

    void deletePayment(Long monthlySalaryPaymentsId);


    List<MonthlySalaryPayment> getMonthlySalariesByMonthlySalaryId(Long monthlySalaryId);
}
