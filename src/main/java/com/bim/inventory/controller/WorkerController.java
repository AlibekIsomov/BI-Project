package com.bim.inventory.controller;

import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.SalaryRecord;
import com.bim.inventory.entity.Worker;
import com.bim.inventory.service.WorkerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/worker")
public class WorkerController{
    @Autowired
    WorkerService workerService;

    @PutMapping("/{workerId}/salary")
    public ResponseEntity<Worker> changeSalary(@PathVariable Long workerId) throws Exception {
        Worker updatedWorker = workerService.changeSalary(workerId);
        return ResponseEntity.ok(updatedWorker);
    }

    @GetMapping("/{workerId}/salary/history")
    public ResponseEntity<List<SalaryRecord>> getSalaryHistory(@PathVariable Long workerId) throws Exception {
        List<SalaryRecord> salaryHistory = workerService.getSalaryHistory(workerId);
        return ResponseEntity.ok(salaryHistory);
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
