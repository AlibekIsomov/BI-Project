package com.bim.inventory.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MonthlyPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal paymentAmount;

    private BigDecimal paidAmount;

    private LocalDate toDate;

    private LocalDate fromDate;

    @ManyToOne
    @JoinColumn(name = "rentStore_id")
    @JsonBackReference
    private RentStore rentStore;

    private PaymentStatus status;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;
}
