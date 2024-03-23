package com.bim.inventory.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleStoreDTO {
    private Long id;

    private Long fullAmount;

    private Long initialPayment;

    private Long StoreId;

    private List<PaymentDTO> payments;

}
