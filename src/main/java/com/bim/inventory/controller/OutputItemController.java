package com.bim.inventory.controller;


import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.InputItemService;
import com.bim.inventory.service.OutputItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
@RestController
@RequestMapping("/api/outputitem")
public class OutputItemController  implements CommonController<OutputItem,Long> {
    @Autowired
    OutputItemService itemService;
    @Autowired
    OutputItemRepository outputItemRepository;

    @Override
    @GetMapping
    public ResponseEntity<Page<OutputItem>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(itemService.getAll(pageable));
    }
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OutputItem> getById(@PathVariable Long id) throws Exception {
        return itemService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<OutputItem> create(@RequestBody OutputItem data) throws Exception {
        return itemService.create(data).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping
    public ResponseEntity<OutputItem> update(@RequestBody OutputItem data) throws Exception {
        return itemService.update(data).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        itemService.deleteById(id);
    }


    @GetMapping("/created-between")
    public List<OutputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return outputItemRepository.findByCreatedAtBetween(fromDate, toDate);
    }

    @GetMapping("/total-price")
    public double getTotalPrice() {
        return itemService.getTotalPrice();
    }
}
