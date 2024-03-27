package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.*;
import com.bim.inventory.repository.*;
import com.bim.inventory.service.StoreService;
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
public class StoreServiceImpl implements StoreService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    RentStoreRepository rentStoreRepository;

    @Autowired
    SaleStoreRepository saleStoreRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FileRepository fileRepository;


    @Override
    public List<Store> getAll(Pageable pageable) throws Exception {
        List<Store> stores = storeRepository.findAll();
//        List<SaleStore> saleStores = saleStoreRepository.findByStoreId(store.getId());

        for (Store store : stores) {
            // paidni tekshirish !
            if (!saleStoreRepository.existsByStoreId(store.getId()) && !rentStoreRepository.existsByStoreId(store.getId())) {
                store.setConnected(false);
            } else {
                store.setConnected(true);
            }


        }
            return stores;
    }


    @Override
    public Optional<Store> getById(Long id) throws Exception {
        if (!storeRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return storeRepository.findById(id);
    }


    @Override
    public Optional<Store> create(StoreDTO data) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(data.getCategoryId());

        if (!optionalCategory.isPresent()) {
            logger.info("Such ID category does not exist!");
        }



        Store store = new Store();
        store.setContractNumber(data.getContractNumber());
        store.setStatus(PaymentStatus.valueOf(data.getStatus()));
        store.setStoreNumber(data.getStoreNumber());
        store.setSize(data.getSize());
        store.setCategory(optionalCategory.get());

        return Optional.of(storeRepository.save(store));
    }

    @Override
    public Optional<Store> update(Long id, StoreDTO data) throws Exception {
        Optional<Store> existingStore = storeRepository.findById(id);

        if (!existingStore.isPresent()) {
            logger.info("Store with id " + id + " does not exist");
            return Optional.empty();
        }

        Optional<Category> optionalCategory = categoryRepository.findById(data.getCategoryId());

        if (!optionalCategory.isPresent()) {
            logger.info("Such ID category does not exist!");
        }
        Store storeToUpdate = existingStore.get();

        FileEntity oldFileEntity = storeToUpdate.getFileEntity();

        // Check if fileId is provided before removing the FileEntity
        if (data.getFileEntityId() != null) {
            Optional<FileEntity> newFileEntityOptional = fileRepository.findById(data.getFileEntityId());

            if (!newFileEntityOptional.isPresent()) {
                logger.info("FileEntity with id " + data.getFileEntityId() + " does not exist");
                return Optional.empty();
            }

            // Set the new FileEntity
            FileEntity newFileEntity = newFileEntityOptional.get();
            storeToUpdate.setFileEntity(newFileEntity);
        } else {
            // If fileId is not provided, remove the old FileEntity
            if (oldFileEntity != null) {
                // Delete the old FileEntity from the repository
                fileRepository.delete(oldFileEntity);
                // Remove the old FileEntity from the Store
                storeToUpdate.setFileEntity(null);
            }
        }


        storeToUpdate.setContractNumber(data.getContractNumber());
        storeToUpdate.setStoreNumber(data.getStoreNumber());
        storeToUpdate.setStatus(PaymentStatus.valueOf(data.getStatus()));
        storeToUpdate.setSize(data.getSize());
        storeToUpdate.setStoreNumber(data.getStoreNumber());
        storeToUpdate.setCategory(optionalCategory.get());

        return Optional.of(storeRepository.save(storeToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        if(!storeRepository.existsById(id)) {
            logger.info("Store with id " + id + " does not exists");
        }

        saleStoreRepository.deleteAll(saleStoreRepository.findAllByStoreId(id));
        rentStoreRepository.deleteAll(rentStoreRepository.findAllByStoreId(id));

        storeRepository.deleteById(id);
    }


    @Override
    public Page<Store> getAllByStoreNumberContains(int storeNumber, Pageable pageable) {
        return storeRepository.findAllByStoreNumber(storeNumber, pageable);
    }


    @Override
    public List<Store> findItemsWithinDateRange(Instant startDate, Instant endDate) {
        return storeRepository.findByCreatedAtBetween(startDate, endDate);

    }

    @Override
    public boolean isStoreConnected(Long storeId) {
        return saleStoreRepository.existsByStoreId(storeId) || rentStoreRepository.existsByStoreId(storeId);
    }

    @Override
    public boolean isEveryStoreConnected() {
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            if (!saleStoreRepository.existsByStoreId(store.getId()) && !rentStoreRepository.existsByStoreId(store.getId())) {
                return false; // If any store does not have either SaleStore or RentStore, return false
            }
        }
        return true; // All stores have either SaleStore or RentStore
    }



}
