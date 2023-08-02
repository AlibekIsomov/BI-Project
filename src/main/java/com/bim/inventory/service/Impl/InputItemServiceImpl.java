package com.bim.inventory.service.Impl;

import com.bim.inventory.entity.InputItem;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.service.InputItemService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InputItemServiceImpl implements InputItemService {
    private final InputItemRepository itemRepository;

    public InputItemServiceImpl(InputItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public InputItem addItem(InputItem item) {
        // Additional validation and error handling can be added here
        return itemRepository.save(item);
    }

    @Override
    public List<InputItem> getAllItems() {
        return itemRepository.findAll();

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






}
