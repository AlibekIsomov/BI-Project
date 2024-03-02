package com.bim.inventory.dto;


import com.bim.inventory.entity.Worker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalaryDTO {

    private Long id;

    private Instant monthDate;

    private String status;

    private Long paymentAmount;

    private Long paidAmount;

    private Long workerId;

    private Instant createdAt;


    @JsonIgnore
    private Worker worker;

    public void setPropertiesForFirstDay() {
        System.out.println("Setting properties for the first day of the month...");

        // Use LocalDate.atStartOfDay(ZoneOffset.UTC) to obtain an Instant at the start of the day
        this.monthDate = LocalDate.now().withDayOfMonth(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        this.status = "PROCESS";

        if (worker != null) {
            if (worker.getCurrentSalary() != null) {
                this.paymentAmount = worker.getCurrentSalary();
            } else {
                this.paymentAmount = 10000L;
            }
        } else {
            // Handle the case where worker is null (assign a default value or throw an exception)
            // For now, let's assign a default value of 10000L
            this.paymentAmount = 10000L;
        }

        this.paidAmount = 0L;
    }



}
