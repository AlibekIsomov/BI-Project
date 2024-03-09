package com.bim.inventory.entity;


import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class InputItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    private String description;

    private int count;

    @ManyToOne
    @JoinColumn(name = "categoryItem_id", nullable = false)
    private CategoryItem categoryItem;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;
}
