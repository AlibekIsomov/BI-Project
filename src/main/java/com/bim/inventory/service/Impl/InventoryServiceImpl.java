package com.bim.inventory.service.Impl;

import com.bim.inventory.dto.InventoryDTO;
import com.bim.inventory.entity.FileEntity;
import com.bim.inventory.entity.Inventory;
import com.bim.inventory.repository.FileRepository;
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
    @Autowired
    FileRepository fileRepository;

    @Override
    public Page<Inventory> getAll(Pageable pageable) throws Exception {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Inventory> getById(Long id) throws Exception {
        if (!inventoryRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return inventoryRepository.findById(id);
    }

    @Override
    public Optional<Inventory> create(InventoryDTO data) throws Exception {

        Optional<FileEntity> optionalFileEntity = fileRepository.findById(data.getFileEntityId());
        if (!optionalFileEntity.isPresent()) {
            logger.info("Such ID category does not exist!");
        }

        Inventory inventory = new Inventory();
        inventory.setName(data.getName());
        inventory.setPrice(data.getPrice());
        inventory.setDescription(data.getDescription());
        inventory.setCount(data.getCount());
        inventory.setFileEntity(optionalFileEntity.get());

        return Optional.of(inventoryRepository.save(inventory));
    }
    @Override
    public Optional<Inventory> update(Long id, InventoryDTO data) throws Exception {
        Optional<Inventory> existingInventory = inventoryRepository.findById(id);
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(data.getFileEntityId());
        if (!existingInventory.isPresent()) {
            logger.info("Inventory with id " + id + " does not exist");
            return null;
        }
        Inventory inventoryUpdate = existingInventory.get();


        inventoryUpdate.setName(data.getName());
        inventoryUpdate.setPrice(data.getPrice());
        inventoryUpdate.setDescription(data.getDescription());
        inventoryUpdate.setCount(data.getCount());
        inventoryUpdate.setFileEntity(optionalFileEntity.get());

        return Optional.of(inventoryRepository.save(inventoryUpdate));
    }
    @Override
    public Page<Inventory> getAllByNameContains(String name, Pageable pageable) {
        return inventoryRepository.findAllByNameContains(name, pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (!inventoryRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
        }
        inventoryRepository.deleteById(id);
    }



    }



