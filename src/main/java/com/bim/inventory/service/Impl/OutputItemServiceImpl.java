package com.bim.inventory.service.Impl;

import com.bim.inventory.entity.OutputItem;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.OutputItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutputItemServiceImpl implements OutputItemService {
    private static final Logger LOG = LoggerFactory.getLogger(OutputItem.class);
        @Autowired
        OutputItemRepository itemRepository;

        @Override
        public OutputItem addItem(OutputItem item) {
            item.setCreatedAt(LocalDateTime.now());
            return itemRepository.save(item);
        }
        @Override
        public List<OutputItem> getAllItems() {
            return itemRepository.findAll();
        }

        @Override
        public OutputItem update(OutputItem item) {
        item.setCreatedAt(LocalDateTime.now());
        return itemRepository.save(item);
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

    @Override
    public boolean delete(Long id) {
        OutputItem item = getById(id);
        if (item == null) {
            LOG.error("Failed to delete entity with ID '{}' as it does not exist", id);
            return false;
        }
        try {
            itemRepository.delete(item);
            return true;
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
    @Override
    public OutputItem getById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }
    }
