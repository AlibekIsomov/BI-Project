package com.bim.inventory.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String jobDescription;
    private double salary;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "worker")
    private List<SalaryRecord> salaryHistory = new ArrayList<>();

}
