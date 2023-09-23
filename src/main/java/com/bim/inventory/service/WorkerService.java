package com.bim.inventory.service;

import com.bim.inventory.dto.InputDTO;
import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.InputItem;
import com.bim.inventory.entity.SalaryRecord;
import com.bim.inventory.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WorkerService extends CommonService<Worker,Long>{
    Worker changeSalary(Long workerId) throws Exception;

    List<SalaryRecord> getSalaryHistory(Long workerId) throws Exception;

    Optional<Worker> create(WorkerDTO data) throws Exception;

    Optional<Worker> update(Long id, WorkerDTO data) throws Exception;

    Page<Worker> getAllByNameAndSurnameContains(String name, String Surname, Pageable pageable);
}
