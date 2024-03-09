package com.bim.inventory.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDTO {

    private String name;

    private double price;

    private String description;

    private int count;

    private Long categoryItemId;

}
