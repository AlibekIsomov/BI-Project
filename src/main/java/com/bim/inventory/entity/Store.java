package com.bim.inventory.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int fullAmount;

    private int contractNumber;

    private String FullName;

    private int storeNumber;

    private int initialPayment;

    private double size;

    private double lastPayment;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;
}