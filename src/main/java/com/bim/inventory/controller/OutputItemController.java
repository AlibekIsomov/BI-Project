package com.bim.inventory.controller;

import com.bim.inventory.dto.OutputDTO;
import com.bim.inventory.entity.OutputItem;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.OutputItemService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/outputitem")
public class OutputItemController  {
    @Autowired
    OutputItemService itemService;
    @Autowired
    OutputItemRepository outputItemRepository;

    @GetMapping
    public ResponseEntity<Page<OutputItem>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(itemService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputItem> getById(@PathVariable Long id) throws Exception {
        return itemService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OutputItem> create(@RequestBody OutputDTO data) {
        try {
            Optional <OutputItem> createditem = itemService.create(data);

            if(createditem.isPresent()){
                return ResponseEntity.ok(createditem.get());
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutputItem> update(@PathVariable Long id,
                                            @RequestBody OutputDTO data) {
        try {
            Optional <OutputItem> updatedItem = itemService.update(id, data);

            if (updatedItem.isPresent()) {
                return ResponseEntity.ok(updatedItem.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException outputNotFoundException) {
            // Handle category not found exception, e.g., return a bad request response
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Handle other exceptions, e.g., log the error and return an internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
    
    @GetMapping("/search-name/{name}")
    public ResponseEntity<Page<OutputItem>> getAllByNameContains(@PathVariable String name, Pageable pageable){
        return ResponseEntity.ok(itemService.getAllByNameContains(name, pageable));
    }

    @GetMapping("/find-by-date-range")
    public List<OutputItem> findItemsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Instant startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Instant endDate) {
        return itemService.findItemsWithinDateRange(startDate, endDate);
    }
}
