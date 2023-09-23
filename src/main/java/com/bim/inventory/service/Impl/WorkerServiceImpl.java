package com.bim.inventory.service.Impl;

import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.SalaryRecord;
import com.bim.inventory.entity.Worker;
import com.bim.inventory.repository.WorkerRepository;
import com.bim.inventory.service.WorkerService;
import javassist.NotFoundException;
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
public class WorkerServiceImpl implements WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    WorkerRepository workerRepository;

    @Override
    public Page<Worker> getAll(Pageable pageable) throws Exception {
        return workerRepository.findAll(pageable);
    }

    @Override
    public Optional<Worker> getById(Long id) throws Exception {
        if(!workerRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
            return Optional.empty();
        }
        return workerRepository.findById(id);
    }

    @Override
    public Optional<Worker> create(WorkerDTO data) throws Exception {

        Worker worker = new Worker();
        worker.setName(data.getName());
        worker.setSurname(data.getSurname());
        worker.setSalary(data.getSalary());

        return Optional.of(workerRepository.save(worker));
    }

    @Override
    public Optional<Worker> update(Long id, WorkerDTO data) throws Exception {

        Worker worker = new Worker();
        worker.setName(data.getName());
        worker.setSurname(data.getSurname());
        worker.setSalary(data.getSalary());

        return Optional.of(workerRepository.save(worker));
    }
    @Override
    public Page<Worker> getAllByNameAndSurnameContains(String name,String surname, Pageable pageable) {
        return workerRepository.findAllByNameAndSurnameContains(name, surname, pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!workerRepository.existsById(id)) {
            logger.info("Input with id " + id + " does not exists");
        }
        workerRepository.deleteById(id);
    }


    public Worker changeSalary(Long workerId) throws Exception{
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new NotFoundException("Worker not found"));

        // Create a new salary record
        SalaryRecord salaryRecord = new SalaryRecord();
        salaryRecord.setSalaryAmount(worker.getSalary()); // Record the existing salary
        salaryRecord.setTimestamp(LocalDateTime.now());
        salaryRecord.setWorker(worker);

        // Update the worker's current salary and add the record to history
        worker.getSalaryHistory().add(salaryRecord);

        // Save the updated worker
        return workerRepository.save(worker);
    }

    public List<SalaryRecord> getSalaryHistory(Long workerId) throws Exception {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new NotFoundException("Worker not found"));

        return worker.getSalaryHistory();
    }
}
