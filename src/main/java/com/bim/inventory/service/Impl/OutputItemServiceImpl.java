package com.bim.inventory.service.Impl;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.InputItemService;
import com.bim.inventory.service.OutputItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutputItemServiceImpl implements OutputItemService {
        @Autowired
        OutputItemRepository itemRepository;

        @Override
        public OutputItem addItem(OutputItem item) {
            // Additional validation and error handling can be added here
            return itemRepository.save(item);
        }

        @Override
        public List<OutputItem> getAllItems() {
            return itemRepository.findAll();

        }

        public List<OutputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate) {
            return itemRepository.findByCreatedAtBetween(fromDate, toDate);
        }

        @Override
        public List<OutputItem> getItemsCreatedAfter(LocalDateTime fromDate) {
            return itemRepository.findByCreatedAtAfter(fromDate);
        }

        @Override
        public double getTotalPrice() {
            List<OutputItem> items = getAllItems();
            double totalPrice = 0.0;
            for (OutputItem item : items) {
                totalPrice += item.getPrice() * item.getCount();
            }
            return totalPrice;
        }
    }
