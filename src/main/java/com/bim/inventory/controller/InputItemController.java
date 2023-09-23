package com.bim.inventory.controller;

import com.bim.inventory.dto.InputDTO;
import com.bim.inventory.entity.InputItem;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.service.InputItemService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/inputitem")
public class InputItemController   {
    @Autowired
    InputItemService itemService;
    @Autowired
    InputItemRepository inputItemRepository;

    @GetMapping
    public ResponseEntity<Page<InputItem>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(itemService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputItem> getById(@PathVariable Long id) throws Exception {
        return itemService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InputItem> create(@RequestBody InputDTO data) {
       try {
           Optional<InputItem> createditem = itemService.create(data);

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
    public ResponseEntity<InputItem> update(@PathVariable Long id,
                                            @RequestBody InputDTO data) {
        try {
            Optional<InputItem> updatedItem = itemService.update(id, data);

            if (updatedItem.isPresent()) {
                return ResponseEntity.ok(updatedItem.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException categoryNotFoundException) {

            return ResponseEntity.badRequest().build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        itemService.deleteById(id);
    }


    @GetMapping("/total-price")
    public double getTotalPrice() {
        return itemService.getTotalPrice();
    }

    @GetMapping("/search-name/{name}")
    public ResponseEntity<Page<InputItem>> searchName(@PathVariable String name, Pageable pageable){
        return ResponseEntity.ok(inputItemRepository.findAllByNameContains(name, pageable));
    }

    @GetMapping("/find-by-date-range")
    public List<InputItem> findItemsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return itemService.findItemsWithinDateRange(startDate, endDate);
    }
}