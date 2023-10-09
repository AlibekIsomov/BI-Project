package com.bim.inventory.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {

    private String name;

    private Long fileEntityId;

    private double price;

    private String description;

    private int count;
}
