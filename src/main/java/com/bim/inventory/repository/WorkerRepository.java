package com.bim.inventory.repository;

import com.bim.inventory.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Page<Worker> findAllByNameAndSurnameContains(String name, String Surname, Pageable pageable);
}
