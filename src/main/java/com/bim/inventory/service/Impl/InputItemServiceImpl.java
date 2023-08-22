package com.bim.inventory.service.Impl;

import com.bim.inventory.dto.InputDTO;
import com.bim.inventory.entity.InputItem;
import com.bim.inventory.repository.InputItemRepository;
import com.bim.inventory.service.InputItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InputItemServiceImpl implements InputItemService {
    private static final Logger logger = LoggerFactory.getLogger(InputItemServiceImpl.class);
    @Autowired
    InputItemRepository itemRepository;

    @Override
    public Page<InputDTO> getAll(Pageable pageable) throws Exception {
        return itemRepository.findAll(pageable).map(InputDTO::new);
    }

    @Override
    public Optional<InputDTO> getById(Long id) throws Exception {
        if(!itemRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return itemRepository.findById(id).map(InputDTO::new);
    }

    @Override
    public Optional<InputDTO> create(InputItem data) throws Exception {
        return Optional.of(itemRepository.save(data)).map(InputDTO::new);
    }

    @Override
    public Optional<InputDTO> update(InputItem data) throws Exception {
        if(itemRepository.existsById(data.getId())) {
            logger.info("Input with id " + data.getId() + " does not exists");
            return Optional.empty();
        }
        return Optional.of(itemRepository.save(data)).map(InputDTO::new);
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

    public List<InputItem> getItemsCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return itemRepository.findByCreatedAtBetween(fromDate, toDate);
    }

    @Override
    public Page<InputDTO> getAllDTO(Pageable pageable) {
        Page<InputItem> inputItems = itemRepository.findAll(pageable);
        Page<InputDTO> inputDTOPage = inputItems.map(InputDTO::new);
        return inputDTOPage;
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
