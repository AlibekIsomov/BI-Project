package com.bim.inventory.controller;

import com.bim.inventory.dto.WorkerDTO;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/worker")
public class WorkerController{
    @Autowired
    WorkerService workerService;
    @Autowired
    WorkerRepository workerRepository;

    @PutMapping("/{id}/update-salary")
    public ResponseEntity<WorkerDTO> updateSalary(
            @PathVariable Long id,
            @RequestParam double newSalary) {
        WorkerDTO updatedWorker = workerService.updateSalary(id, newSalary);
        return ResponseEntity.ok(updatedWorker);
    }

    @GetMapping("/{id}/salary-history")
    public ResponseEntity<WorkerDTO> getSalaryHistory(@PathVariable Long id) {
        WorkerDTO workerHistory = workerService.getWorkerSalaryHistory(id);
        return ResponseEntity.ok(workerHistory);
    }


    @GetMapping
    public ResponseEntity<Page<Worker>> getAll(Pageable pageable) throws Exception {
        return ResponseEntity.ok(workerService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> getById(@PathVariable Long id) throws Exception {
        return workerService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
