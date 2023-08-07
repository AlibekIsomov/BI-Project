package com.bim.inventory.repository;


import com.bim.inventory.entity.InputItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<InputItem, Long> {
}
