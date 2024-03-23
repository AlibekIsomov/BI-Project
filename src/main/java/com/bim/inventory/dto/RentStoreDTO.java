package com.bim.inventory.dto;


import com.bim.inventory.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentStoreDTO {
    private Long id;

    private BigDecimal PaymentAmount;

    private Long expiryMonth;

    private Long StoreId;

}
