package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    PaymentRepository paymentRepository;


    @Override
    public Page<Payment> getAll(Pageable pageable) throws Exception {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public Optional<Payment> getById(Long id) throws Exception {
        if (!paymentRepository.existsById(id)) {
            logger.info("Payment with id " + id + " does not exists");
            return Optional.empty();
        }
        return paymentRepository.findById(id);
    }


    @Override
    public Optional<Payment> create(PaymentDTO data) throws Exception {

        Payment payment = new Payment();
        payment.setNewPayment(data.getNewPayment());

        return Optional.of(paymentRepository.save(payment));
    }


    @Override
    public void deleteById(Long id) {
        if(!paymentRepository.existsById(id)) {
            logger.info("Payment with id " + id + " does not exists");
        }
        paymentRepository.deleteById(id);
    }


}
