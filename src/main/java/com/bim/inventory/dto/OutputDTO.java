package com.bim.inventory.dto;

import com.bim.inventory.entity.OutputItem;
import java.time.LocalDate;

public class OutputDTO {
    private Long id;

    private String name;

    private Long categoryId;

    private double price;

    private int count;

    private String description;

    private LocalDate date;


    public OutputDTO() {
    }

    public OutputDTO(OutputItem Outputitem) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.count = count;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}


