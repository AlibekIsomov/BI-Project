package com.bim.inventory.dto;


import com.bim.inventory.entity.RentStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPaymentDTO {

    private Long Id;

    private BigDecimal paymentAmount;

    private BigDecimal paidAmount;

    private LocalDate toDate;

    private LocalDate fromDate;

    private String status;

    private Long rentStoreId;

    private Instant createdAt;
}
