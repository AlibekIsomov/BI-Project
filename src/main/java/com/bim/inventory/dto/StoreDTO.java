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

    private int contractNumber;

    private String fullName;

    private int storeNumber;

    private double size;

    private double lastPayment;

    private Long categoryId;

    private Long fileEntityId;

    private String status;

}
