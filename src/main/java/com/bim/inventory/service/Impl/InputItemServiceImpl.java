package com.bim.inventory.service.Impl;

import com.bim.inventory.dto.InputDTO;
import com.bim.inventory.entity.Category;
import com.bim.inventory.entity.CategoryItem;
import com.bim.inventory.entity.InputItem;
import com.bim.inventory.repository.CategoryItemRepository;
import com.bim.inventory.repository.CategoryRepository;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.service.InputItemService;
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
public class InputItemServiceImpl implements InputItemService {
    private static final Logger logger = LoggerFactory.getLogger(InputItemServiceImpl.class);
    @Autowired
    InputItemRepository itemRepository;

    @Autowired
    CategoryItemRepository categoryItemRepository;

    @Override
    public Page<InputItem> getAll(Pageable pageable) throws Exception {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Optional<InputItem> getById(Long id) throws Exception {
        if (!itemRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return itemRepository.findById(id);
    }


    @Override
    public Optional<InputItem> create(InputDTO data) throws Exception {
        Optional<CategoryItem> optionalCategory = categoryItemRepository.findById(data.getCategoryItemId());

        if (!optionalCategory.isPresent()) {
            logger.info("Such ID category does not exist!");
        }

        InputItem item = new InputItem();
        item.setName(data.getName());
        item.setPrice(data.getPrice());
        item.setDescription(data.getDescription());
        item.setCount(data.getCount());
        item.setCategoryItem(optionalCategory.get());

        return Optional.of(itemRepository.save(item));
    }

    @Override
    public Optional<InputItem> update(Long id, InputDTO data) throws Exception {
        Optional<InputItem> existingItem = itemRepository.findById(id);

        if (!existingItem.isPresent()) {
            logger.info("Input with id " + id + " does not exist");
            return null;
        }

        Optional<CategoryItem> optionalCategory = categoryItemRepository.findById(data.getCategoryItemId());

        if (!optionalCategory.isPresent()) {
            logger.info("Such ID category does not exist!");
        }

        InputItem itemToUpdate = existingItem.get();

        itemToUpdate.setName(data.getName());
        itemToUpdate.setPrice(data.getPrice());
        itemToUpdate.setDescription(data.getDescription());
        itemToUpdate.setCount(data.getCount());
        itemToUpdate.setCategoryItem(optionalCategory.get());

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
    public Page<InputItem> getAllByNameContains(String name, Pageable pageable) {
        return itemRepository.findAllByNameContains(name, pageable);
    }

    @Override
    public List<InputItem> getAllItems() {
        return itemRepository.findAll();

    }
    @Override
    public List<InputItem> findItemsWithinDateRange(Instant startDate, Instant endDate) {
        return itemRepository.findByCreatedAtBetween(startDate, endDate);
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
