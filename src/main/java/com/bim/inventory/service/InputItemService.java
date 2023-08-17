package com.bim.inventory.service;

import com.bim.inventory.dto.InputDTO;
import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;
public interface InputItemService  extends CommonService<InputItem, Long>  {

    List<InputItem> getAllItems();
    double getTotalPrice();

    List<InputItem> getItemsCreatedAfter(LocalDateTime fromDate);
    List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);

    public Page<InputDTO> getAllDTO(Pageable pageable);

}

