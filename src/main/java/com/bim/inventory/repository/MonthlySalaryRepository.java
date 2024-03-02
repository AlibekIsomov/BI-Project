package com.bim.inventory.repository;


import com.bim.inventory.entity.MonthlySalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlySalaryRepository extends JpaRepository<MonthlySalary, Long> {
    Iterable<? extends MonthlySalary> findAllByWorkerId(Long id);

//    @Query("SELECT ms FROM MonthlySalary ms WHERE ms.worker = :workerId")
//    List<MonthlySalary> findByWorkerId(@Param("workerId") Long workerId);

    List<MonthlySalary> findByWorkerId(Long workerId);
}
