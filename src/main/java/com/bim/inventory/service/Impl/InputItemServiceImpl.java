package com.bim.inventory.service.Impl;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.OutputItem;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.InputItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InputItemServiceImpl implements InputItemService {
    private static final Logger LOG = LoggerFactory.getLogger(InputItemServiceImpl.class);
    @Autowired
    InputItemRepository itemRepository;

    @Override
    public InputItem addItem(InputItem item) {
        // Additional validation and error handling can be added here
        return itemRepository.save(item);
    }

    @Override
    public List<InputItem> getAllItems() {
        return itemRepository.findAll();

    }

    @Override
    public InputItem update(InputItem item) {
        item.setCreatedAt(LocalDateTime.now());
        return itemRepository.save(item);
    }

    public List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return itemRepository.findByCreatedAtBetween(fromDate, toDate);
    }

    @Override
    public List<InputItem> getItemsCreatedAfter(LocalDateTime fromDate) {
        return itemRepository.findByCreatedAtAfter(fromDate);
    }

    @Override
    public double getTotalPrice() {
        List<InputItem> items = getAllItems();
        double totalPrice = 0.0;
        for (InputItem item : items) {
            totalPrice += item.getPrice() * item.getCount();
        }
        return totalPrice;
    }


    @Override
    public boolean delete(Long id) {
        InputItem item = getById(id);
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
    public InputItem getById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }






}
