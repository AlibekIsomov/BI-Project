package com.bim.inventory.repository;



import com.bim.inventory.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Page<Category> findAllByOrderByIdDesc(Pageable pageable);
}
