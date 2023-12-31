package com.bim.inventory.dto;


import com.bim.inventory.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private Long id;

    private int fullAmount;

    private int contractNumber;

    private String fullName;

    private int storeNumber;

    private int initialPayment;

    private double size;

    private double lastPayment;

    private List<PaymentDTO> payments;

}