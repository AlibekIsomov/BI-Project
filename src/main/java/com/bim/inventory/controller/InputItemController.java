package com.bim.inventory.controller;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
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

    @Override
    @GetMapping
    public ResponseEntity<Page<InputItem>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(itemService.getAll(pageable));
    }
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<InputItem> getById(@PathVariable Long id) throws Exception {
        return itemService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<InputItem> create(@RequestBody InputItem data) throws Exception {
        return itemService.create(data).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping
    public ResponseEntity<InputItem> update(@RequestBody InputItem data) throws Exception {
        return itemService.update(data).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        itemService.deleteById(id);
    }

    @GetMapping("/created-between")
    public List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return inputItemRepository.findByCreatedAtBetween(fromDate, toDate);
    }

    @GetMapping("/total-price")
    public double getTotalPrice() {
        return itemService.getTotalPrice();
    }


}