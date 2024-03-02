package com.bim.inventory.service;

import com.bim.inventory.dto.MonthlySalaryDTO;
import com.bim.inventory.entity.MonthlySalary;
import com.bim.inventory.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MonthlySalaryService {
    Optional<MonthlySalary> create(MonthlySalaryDTO data) throws Exception;

    Optional<MonthlySalary> update(Long id, MonthlySalaryDTO data) throws Exception;

    void deleteById(Long id);

    Page<MonthlySalary> getAll(Pageable pageable) throws Exception;


    List<MonthlySalary> getMonthlySalariesByWorkerId(Long workerId);

    Optional<MonthlySalary> getById(Long id) throws Exception;
}
