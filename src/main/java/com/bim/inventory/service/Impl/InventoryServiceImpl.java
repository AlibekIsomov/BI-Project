package com.bim.inventory.service.Impl;

import com.bim.inventory.entity.Inventory;
import com.bim.inventory.repository.InventoryRepository;
import com.bim.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public Page<Inventory> getAll(Pageable pageable) throws Exception {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Inventory> getById(Long id) throws Exception {
        if(!inventoryRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return inventoryRepository.findById(id);
    }

    @Override
    public Optional<Inventory> create(Inventory data) throws Exception {
        return Optional.of(inventoryRepository.save(data));
    }

    @Override
    public Optional<Inventory> update(Inventory data) throws Exception {
        if(inventoryRepository.existsById(data.getId())) {
            logger.info("Input with id " + data.getId() + " does not exists");
            return Optional.empty();
        }
        return Optional.of(inventoryRepository.save(data));
    }
    @Override
    public Page<Inventory> getAllByNameContains(String name, Pageable pageable) {
        return inventoryRepository.findAllByNameContains(name,pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!inventoryRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
        }
        inventoryRepository.deleteById(id);
    }
}
