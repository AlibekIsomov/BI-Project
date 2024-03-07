package com.bim.inventory.service.Impl;

import com.bim.inventory.dto.MonthlySalaryDTO;
import com.bim.inventory.dto.WorkerDTO;
import com.bim.inventory.entity.*;
import com.bim.inventory.repository.FileRepository;
import com.bim.inventory.repository.MonthlySalaryPaymentRepository;
import com.bim.inventory.repository.MonthlySalaryRepository;
import com.bim.inventory.repository.WorkerRepository;
import com.bim.inventory.service.WorkerService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class WorkerServiceImpl implements WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    MonthlySalaryRepository monthlySalaryRepository;

    @Autowired
    MonthlySalaryPaymentRepository monthlySalaryPaymentRepository;

    @Autowired
    FileRepository fileRepository;

    @Override
    public Page<Worker> getAll(Pageable pageable) throws Exception {
        return workerRepository.findAll(pageable);
    }

    public List<Worker> getAllWorker()  {
        return workerRepository.findAll();
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
        // Create an initial salary change

        return Optional.of(workerRepository.save(worker));
    }

    @Override
    public Optional<Worker> update(Long id, WorkerDTO data) throws Exception {
        Optional<Worker> optionalWorker = workerRepository.findById(id);

        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();

            FileEntity oldFileEntity = worker.getFileEntity();
            // Check if fileId is provided before removing the FileEntity
            if (data.getFileEntityId() != null) {
                Optional<FileEntity> newFileEntityOptional = fileRepository.findById(data.getFileEntityId());

                if (!newFileEntityOptional.isPresent()) {
                    logger.info("FileEntity with id " + data.getFileEntityId() + " does not exist");
                    return Optional.empty();
                }

                if(oldFileEntity!=null) {
                    fileRepository.delete(oldFileEntity);
                }
                // Set the new FileEntity
                FileEntity newFileEntity = newFileEntityOptional.get();
                worker.setFileEntity(newFileEntity);
            }



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
        List<MonthlySalaryPayment> paymentsToDelete = monthlySalaryPaymentRepository.findAllByMonthlySalaryId(id);
        monthlySalaryPaymentRepository.deleteAll(paymentsToDelete);
        monthlySalaryRepository.deleteAll(monthlySalaryRepository.findAllByWorkerId(id));
        workerRepository.deleteById(id);
    }
    @Override
    @Transactional
    public Optional<MonthlySalary> createForSchedule(MonthlySalaryDTO data) throws Exception {

        List<Worker> allWorkers = workerRepository.findAll();

        for (Worker worker : allWorkers) {

            worker.setCurrentSalary(data.getPaymentAmount());
            workerRepository.save(worker);


            MonthlySalary monthlySalary = new MonthlySalary();

            data.setPropertiesForFirstDay();
            monthlySalary.setMonthDate(data.getMonthDate());
            monthlySalary.setStatus(PaymentStatus.valueOf(data.getStatus()));
            monthlySalary.setPaymentAmount(data.getPaymentAmount());
            monthlySalary.setPaidAmount(data.getPaidAmount());
            monthlySalary.setWorker(worker);


            return Optional.of(monthlySalaryRepository.save(monthlySalary));
        }
        return Optional.empty();
    }
}
