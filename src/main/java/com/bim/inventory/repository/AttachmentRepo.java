package com.bim.inventory.repository;


import com.bim.inventory.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepo extends JpaRepository<Attachment,Integer> {
}
