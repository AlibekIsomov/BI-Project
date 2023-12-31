package com.bim.inventory.repository;

import com.bim.inventory.entity.Payment;
import com.bim.inventory.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment , Long> {
    List<Payment> findByStore(Store store);

    @Query("SELECT COALESCE(SUM(p.newPayment), 0) FROM Payment p WHERE p.store = :store")
    double calculateTotalPaymentsByStore(@Param("store") Store store);

}

