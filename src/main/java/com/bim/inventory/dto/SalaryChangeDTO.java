package com.bim.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryChangeDTO {
    private double oldSalary;
    private double newSalary;
    private Date changeDate;
}
