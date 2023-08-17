package com.bim.inventory.controller;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.service.InputItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/inputitem")
public class InputItemController  implements CommonController<InputItem,Long> {
    @Autowired
    InputItemService itemService;
    @Autowired
    InputItemRepository inputItemRepository;

    @GetMapping("/created-between")
    public List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return inputItemRepository.findByCreatedAtBetween(fromDate, toDate);
    }

    @GetMapping("/total-price")
    public double getTotalPrice() {
        return itemService.getTotalPrice();
    }

    @Override
    public ResponseEntity<Page<InputItem>> getAll(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<InputItem> getById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<InputItem> create(InputItem data) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<InputItem> update(InputItem data) throws Exception {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}