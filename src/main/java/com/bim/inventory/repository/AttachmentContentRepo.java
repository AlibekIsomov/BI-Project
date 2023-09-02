package com.bim.inventory.repository;


import com.bim.inventory.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentContentRepo extends JpaRepository<AttachmentContent,Integer> {
}
