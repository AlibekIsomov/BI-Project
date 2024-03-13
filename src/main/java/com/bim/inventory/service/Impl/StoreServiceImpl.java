package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.StoreDTO;
import com.bim.inventory.entity.*;
import com.bim.inventory.repository.CategoryRepository;
import com.bim.inventory.repository.FileRepository;
import com.bim.inventory.repository.PaymentRepository;
import com.bim.inventory.repository.StoreRepository;
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

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class StoreServiceImpl implements StoreService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FileRepository fileRepository;



    @Override
    public Page<Store> getAll(Pageable pageable) throws Exception {
        return storeRepository.findAll(pageable);
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
        store.setFullAmount(data.getFullAmount());
        store.setContractNumber(data.getContractNumber());
        store.setFullName(data.getFullName());
        store.setInitialPayment(data.getInitialPayment());
        store.setStatus(PaymentStatus.valueOf(data.getStatus()));
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
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(data.getFileEntityId());

        if (!optionalFileEntity.isPresent()) {
            logger.info("Such ID file does not exist!");
        }

        Store storeToUpdate = existingStore.get();

        storeToUpdate.setContractNumber(data.getContractNumber());
        storeToUpdate.setFullName(data.getFullName());
        storeToUpdate.setStoreNumber(data.getStoreNumber());
        storeToUpdate.setInitialPayment(data.getInitialPayment());
        storeToUpdate.setStatus(PaymentStatus.valueOf(data.getStatus()));
        storeToUpdate.setSize(data.getSize());
        storeToUpdate.setCategory(optionalCategory.get());
        storeToUpdate.setFileEntity(optionalFileEntity.get());

        return Optional.of(storeRepository.save(storeToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        if(!storeRepository.existsById(id)) {
            logger.info("Store with id " + id + " does not exists");
        }
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



}
