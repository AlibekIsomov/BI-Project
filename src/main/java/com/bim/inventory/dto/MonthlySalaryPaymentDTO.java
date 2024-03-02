package com.bim.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalaryPaymentDTO {

    private Long paymentAmount;

    private Long monthlySalaryId;
}
