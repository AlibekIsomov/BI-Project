package com.bim.inventory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class OutputItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    private String description;

    @Column(nullable = false)
    private int count;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;




}
