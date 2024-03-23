package com.bim.inventory.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RentStore  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal PaymentAmount;

    @Column(nullable=false)
    private Long expiryMonth;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;


    @OneToMany(mappedBy = "rentStore", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<MonthlyPayment> monthlyPayments = new ArrayList<>();

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;
}
