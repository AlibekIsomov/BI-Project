package com.bim.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO {
  private Long id;

  private String name;

  private String surname;

  private double currentSalary;

  private String jobDescription;


  private List<SalaryChangeDTO> salaryChanges;

}
