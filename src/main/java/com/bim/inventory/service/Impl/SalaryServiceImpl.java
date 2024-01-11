package com.bim.inventory.service.Impl;


import com.bim.inventory.dto.SalaryDTO;
import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.Salary;
import com.bim.inventory.entity.Worker;
import com.bim.inventory.repository.SalaryRepository;
import com.bim.inventory.repository.WorkerRepository;
import com.bim.inventory.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    SalaryRepository salaryRepository;

    @Override
    public ResponseEntity<Salary> addSalary(Long workerId, double newSalary) {

        Optional<Worker> salaryOptional = workerRepository.findById(workerId);

        if (salaryOptional.isPresent()) {
            Worker worker = salaryOptional.get();


            // Create a new payment record for the new payment
            Salary salary = new Salary();
            salary.setNewSalary(newSalary);
            salary.setWorker(worker);

            // Add the new payment to the store's list of payments
            worker.getSalaryChanges().add(salary);

            // Update the store's lastPayment to the new payment
            worker.setCurrentSalary(newSalary);

            // Save the updated store (including the new payment)
            workerRepository.save(worker);

            return ResponseEntity.ok(salary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Salary> updateSalary(Long workerId, Long salaryId, double newSalary) {
        Optional<Worker> workerOptional = workerRepository.findById(workerId);

        if (workerOptional.isPresent()) {
            Worker worker = workerOptional.get();

            // Find the existing payment by ID
            Optional<Salary> salaryOptional = worker.getSalaryChanges().stream()
                    .filter(salary -> salary.getId().equals(salaryId))
                    .findFirst();

            if (salaryOptional.isPresent()) {
                Salary existingSalary = salaryOptional.get();

                // Update the existing payment
                existingSalary.setNewSalary(newSalary);

                // Update the store's lastPayment to the new payment
                worker.setCurrentSalary(newSalary);

                // Save the updated store (including the updated payment)
                workerRepository.save(worker);

                // Convert and return the updated store as a DTO
                return ResponseEntity.ok(existingSalary);
            } else {
                // Handle the case where the specified payment ID is not found
                return ResponseEntity.notFound().build();
            }
        } else {
            // Handle the case where the specified store ID is not found
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public void deleteSalary(Long salaryId) {
        Optional<Salary> salaryOptional = salaryRepository.findById(salaryId);

        if (salaryOptional.isPresent()) {
            Salary salaryToDelete = salaryOptional.get();

            // Remove the payment from the associated store's payments list
            Worker worker = salaryToDelete.getWorker();
            if (worker != null) {
                worker.getSalaryChanges().remove(salaryToDelete);
            }

            // Delete the payment from the database
            salaryRepository.delete(salaryToDelete);
        } else {
            // Handle the case where the payment with the given id is not found
            // You can throw an exception, log a message, or handle it in another way.
            // For simplicity, I'll log a message.
            System.out.println("Payment with id " + salaryId + " not found");
        }
    }

    @Override
    public ResponseEntity<List<SalaryDTO>> getAllSalary (Long workerId) {
        Optional<Worker> workerOptional = workerRepository.findById(workerId);

        if (workerOptional.isPresent()) {
            Worker worker = workerOptional.get();

            List<SalaryDTO> salaryDTOS = worker.getSalaryChanges()
                    .stream()
                    .map(this::convertToSalaryDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(salaryDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    public WorkerDTO convertToDTO(Worker worker) {
        WorkerDTO workerDTO = new WorkerDTO();
        workerDTO.setId(worker.getId());
        workerDTO.setName(worker.getName());
        workerDTO.setSurname(worker.getSurname());
        workerDTO.setJobDescription(worker.getJobDescription());
        workerDTO.setCurrentSalary(worker.getCurrentSalary());

        List<SalaryDTO> salaryChangeDTOs = worker.getSalaryChanges()
                .stream()
                .map(this::convertToSalaryDTO)
                .collect(Collectors.toList());
        workerDTO.setSalaryChanges(salaryChangeDTOs);

        return workerDTO;
    }

    private SalaryDTO convertToSalaryDTO(Salary salaryChange) {
        SalaryDTO salaryChangeDTO = new SalaryDTO();
        salaryChangeDTO.setNewSalary(salaryChange.getNewSalary());
        salaryChangeDTO.setChangeDate(salaryChange.getChangeDate());
        return salaryChangeDTO;
    }
}
