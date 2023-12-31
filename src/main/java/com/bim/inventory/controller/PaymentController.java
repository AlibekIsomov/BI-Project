package com.bim.inventory.controller;


import com.bim.inventory.dto.PaymentDTO;
import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.SalaryChange;
import com.bim.inventory.entity.Worker;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.service.PaymentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping
    public ResponseEntity<Page<Payment>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(paymentService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) throws Exception {
        return paymentService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody PaymentDTO data) throws Exception {
        try {
            Optional<Payment> createdPayment = paymentService.create(data);

            if(createdPayment.isPresent()){
                return ResponseEntity.ok(createdPayment.get());
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Payment> update(@PathVariable Long id,
//                                        @RequestBody PaymentDTO data) throws Exception {
//        try {
//            Optional<Payment> createdPayment = paymentService.update(id, data);
//
//            if (createdPayment.isPresent()) {
//                return ResponseEntity.ok(createdPayment.get());
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (NotFoundException categoryNotFoundException) {
//
//            return ResponseEntity.badRequest().build();
//        } catch (Exception e) {
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        paymentService.deleteById(id);
    }

}