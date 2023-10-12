package com.bim.inventory.controller;

import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.SalaryChange;
import com.bim.inventory.entity.Worker;
import com.bim.inventory.repository.WorkerRepository;
import com.bim.inventory.service.WorkerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/worker")
public class WorkerController{
    @Autowired
    WorkerService workerService;
    @Autowired
    WorkerRepository workerRepository;

    @PutMapping("/{workerId}/update-salary")
    public ResponseEntity<WorkerDTO> updateSalary(
            @PathVariable Long workerId,
            @RequestParam double newSalary) {

        Optional<Worker> workerOptional = workerRepository.findById(workerId);

        if (workerOptional.isPresent()) {
            Worker worker = workerOptional.get();

            // Check if the list of salary changes is empty
            if (worker.getSalaryChanges().isEmpty()) {
                // Create a new salary change record for the initial current salary
                SalaryChange initialSalaryChange = new SalaryChange();
                initialSalaryChange.setNewSalary(worker.getCurrentSalary());
                initialSalaryChange.setChangeDate(new Date());
                initialSalaryChange.setWorker(worker);

                // Add the initial salary change to the worker's list of salary changes
                worker.getSalaryChanges().add(initialSalaryChange);
            }

            // Create a new salary change record for the new salary
            SalaryChange salaryChange = new SalaryChange();
            salaryChange.setNewSalary(newSalary);
            salaryChange.setChangeDate(new Date());
            salaryChange.setWorker(worker);

            // Add the new salary change to the worker's list of salary changes
            worker.getSalaryChanges().add(salaryChange);

            // Update the worker's current salary
            worker.setCurrentSalary(newSalary);

            // Save the updated worker
            workerRepository.save(worker);

            // Convert and return the updated worker as a DTO
            WorkerDTO updatedWorkerDTO = workerService.convertToDTO(worker);
            return ResponseEntity.ok(updatedWorkerDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<WorkerDTO> getbyid(@PathVariable Long id) {
        WorkerDTO workerHistory = workerService.getbyid(id);
        return ResponseEntity.ok(workerHistory);
    }


    @GetMapping
    public ResponseEntity<Page<Worker>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(workerService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Worker> create(@RequestBody WorkerDTO data) {
        try {
            Optional<Worker> createdWorker = workerService.create(data);

            if(createdWorker.isPresent()){
                return ResponseEntity.ok(createdWorker.get());
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Worker> update(@PathVariable Long id,
                                         @RequestBody WorkerDTO data) {
        try {
            Optional<Worker> updatedWorker = workerService.update(id, data);

            if (updatedWorker.isPresent()) {
                return ResponseEntity.ok(updatedWorker.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException categoryNotFoundException) {

            return ResponseEntity.badRequest().build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        workerService.deleteById(id);
    }

    @GetMapping("/search-name/{name}")
    public ResponseEntity<Page<Worker>> searchName(@PathVariable String name, String surname, Pageable pageable) {
        return ResponseEntity.ok(workerService.getAllByNameAndSurnameContains(name, surname, pageable));
    }
}
