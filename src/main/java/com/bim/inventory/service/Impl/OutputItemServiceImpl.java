package com.bim.inventory.service.Impl;

import com.bim.inventory.dto.OutputDTO;
import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.OutputItem;
import com.bim.inventory.repository.CategoryRepository;
import com.bim.inventory.repository.OutputItemRepository;
import com.bim.inventory.service.OutputItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OutputItemServiceImpl implements OutputItemService {


    private static final Logger logger = LoggerFactory.getLogger(OutputItem.class);
    @Autowired
    OutputItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Page<OutputItem> getAll(Pageable pageable) throws Exception {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Optional<OutputItem> getById(Long id) throws Exception {
        if(!itemRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return itemRepository.findById(id);
    }

    @Override
    public Optional<OutputItem> create(OutputDTO data) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(data.getCategoryId());

        if (!optionalCategory.isPresent()) {
            logger.info("Such ID category does not exist!");
        }

        OutputItem item = new OutputItem();
        item.setName(data.getName());
        item.setPrice(data.getPrice());
        item.setDescription(data.getDescription());
        item.setCount(data.getCount());
        item.setCategory(optionalCategory.get());


        return Optional.of(itemRepository.save(item));
    }


    @Override
    public Optional<OutputItem> update(Long id, OutputDTO data) throws Exception {
        Optional<OutputItem> existingItem = itemRepository.findById(id);

        if (!existingItem.isPresent()) {
            logger.info("Input with id " + id + " does not exist");
            return null;
        }

        Optional<Category> optionalCategory = categoryRepository.findById(data.getCategoryId());

        if (!optionalCategory.isPresent()) {
            logger.info("Such ID category does not exist!");
        }

        OutputItem itemToUpdate = existingItem.get();

        itemToUpdate.setName(data.getName());
        itemToUpdate.setPrice(data.getPrice());
        itemToUpdate.setDescription(data.getDescription());
        itemToUpdate.setCount(data.getCount());
        itemToUpdate.setCategory(optionalCategory.get());

        return Optional.of(itemRepository.save(itemToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        if(!itemRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
        }
        itemRepository.deleteById(id);
    }


    @Override
    public Page<OutputItem> getAllByNameContains(String name, Pageable pageable) {
        return itemRepository.findAllByNameContains(name, pageable);
    }

    @Override
    public List<OutputItem> getAllItems() {
        return itemRepository.findAll();

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
    public List<OutputItem> findItemsWithinDateRange(Instant startDate, Instant endDate) {
        return itemRepository.findByCreatedAtBetween(startDate, endDate);
    }
}
