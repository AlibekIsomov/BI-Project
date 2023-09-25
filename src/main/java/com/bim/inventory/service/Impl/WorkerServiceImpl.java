package com.bim.inventory.service.Impl;

import com.bim.inventory.dto.SalaryChangeDTO;
import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.SalaryChange;
import com.bim.inventory.entity.Worker;
import com.bim.inventory.repository.WorkerRepository;
import com.bim.inventory.service.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        return Optional.of(workerRepository.save(worker));
    }

    @Override
    public Optional<Worker> update(Long id, WorkerDTO data) throws Exception {

        Worker worker = new Worker();
        worker.setName(data.getName());
        worker.setSurname(data.getSurname());

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
    @Override
    public WorkerDTO updateSalary(Long workerId, double newSalary) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new IllegalArgumentException("Worker not found"));

        double oldSalary = worker.getSalaryChanges().isEmpty()
                ? worker.getInitialSalary()
                : worker.getSalaryChanges().get(worker.getSalaryChanges().size() - 1).getNewSalary();

        SalaryChange salaryChange = new SalaryChange();
        salaryChange.setOldSalary(oldSalary);
        salaryChange.setNewSalary(newSalary);
        salaryChange.setChangeDate(new Date());
        salaryChange.setWorker(worker);

        worker.getSalaryChanges().add(salaryChange);

        workerRepository.save(worker);

        return convertToDTO(worker);
    }

    @Override
    public WorkerDTO getWorkerSalaryHistory(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new IllegalArgumentException("Worker not found"));

        return convertToDTO(worker);
    }
    @Override
    public WorkerDTO convertToDTO(Worker worker) {
        WorkerDTO workerDTO = new WorkerDTO();
        workerDTO.setId(worker.getId());
        workerDTO.setName(worker.getName());

        List<SalaryChangeDTO> salaryChangesDTO = worker.getSalaryChanges().stream()
                .map(salaryChange -> {
                    SalaryChangeDTO dto = new SalaryChangeDTO();
                    dto.setOldSalary(salaryChange.getOldSalary());
                    dto.setNewSalary(salaryChange.getNewSalary());
                    dto.setChangeDate(salaryChange.getChangeDate());
                    return dto;
                })
                .collect(Collectors.toList());

        workerDTO.setSalaryChanges(salaryChangesDTO);

        return workerDTO;
    }



}
