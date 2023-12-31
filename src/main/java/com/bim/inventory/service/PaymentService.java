package com.bim.inventory.service;

import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Optional<Payment> create(PaymentDTO data) throws Exception;

    Page<Payment> getAll(Pageable pageable) throws Exception;

    Optional<Payment> getById(Long id) throws Exception;

//    Optional<Payment> update(Long id, PaymentDTO data) throws Exception;

    void deleteById(Long id);


}
