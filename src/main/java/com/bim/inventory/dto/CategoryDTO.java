package com.bim.inventory.dto;


import com.bim.inventory.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String locationName;

    private List<Long> FileEntityIds;
}
