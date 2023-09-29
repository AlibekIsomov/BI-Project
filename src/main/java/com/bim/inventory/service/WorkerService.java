package com.bim.inventory.service;

import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkerService extends CommonService<Worker,Long>{
    Optional<Worker> create(WorkerDTO data) throws Exception;

    Optional<Worker> update(Long id, WorkerDTO data) throws Exception;

    Page<Worker> getAllByNameAndSurnameContains(String name, String Surname, Pageable pageable);

    WorkerDTO getbyid(Long workerId);

    WorkerDTO convertToDTO(Worker worker);
}
