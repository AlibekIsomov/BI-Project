package com.bim.inventory.service.Impl;

import com.bim.inventory.dto.SalaryChangeDTO;
import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.SalaryChange;
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
        worker.setJobDescription(data.getJobDescription());
        worker.setCurrentSalary(data.getCurrentSalary());
        // Create an initial salary change
        SalaryChange initialSalaryChange = new SalaryChange();
        initialSalaryChange.setNewSalary(worker.getCurrentSalary());
        initialSalaryChange.setChangeDate(new Date());
        initialSalaryChange.setWorker(worker);

        // Add the initial salary change to the worker's list of salary changes
        worker.getSalaryChanges().add(initialSalaryChange);

        return Optional.of(workerRepository.save(worker));
    }

    @Override
    public Optional<Worker> update(Long id, WorkerDTO data) throws Exception {
        Optional<Worker> optionalWorker = workerRepository.findById(id);

        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();

            // Update the worker's name and surname based on the data from WorkerDTO
            worker.setName(data.getName());
            worker.setSurname(data.getSurname());
            worker.setJobDescription(data.getJobDescription());

            // Save the updated worker
            return Optional.of(workerRepository.save(worker));
        } else {
            // Handle the case where the worker with the given ID doesn't exist
            throw new NotFoundException("Worker not found with ID: " + id);
        }
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
    public WorkerDTO getbyid(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new IllegalArgumentException("Worker not found"));

        return convertToDTO(worker);
    }

    @Override
    public WorkerDTO convertToDTO(Worker worker) {
        WorkerDTO workerDTO = new WorkerDTO();
        workerDTO.setId(worker.getId());
        workerDTO.setName(worker.getName());
        workerDTO.setSurname(worker.getSurname());
        workerDTO.setJobDescription(worker.getJobDescription());
        workerDTO.setCurrentSalary(worker.getCurrentSalary());

        List<SalaryChangeDTO> salaryChangeDTOs = worker.getSalaryChanges()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        workerDTO.setSalaryChanges(salaryChangeDTOs);

        return workerDTO;
    }

    private SalaryChangeDTO convertToDTO(SalaryChange salaryChange) {
        SalaryChangeDTO salaryChangeDTO = new SalaryChangeDTO();
        salaryChangeDTO.setNewSalary(salaryChange.getNewSalary());
        salaryChangeDTO.setChangeDate(salaryChange.getChangeDate());
        return salaryChangeDTO;
    }



}
