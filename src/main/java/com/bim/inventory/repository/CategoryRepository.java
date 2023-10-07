package com.bim.inventory.repository;



import com.bim.inventory.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByNameContains(String name, Pageable pageable);

    @Query("SELECT c FROM Category c JOIN c.inputItems i JOIN c.outputItems o WHERE i.id = :inputItemId AND o.id = :outputItemId")
    List<Category> findByInputItemIdAndOutputItemId(@Param("inputItemId") Long inputItemId, @Param("outputItemId") Long outputItemId);


}
