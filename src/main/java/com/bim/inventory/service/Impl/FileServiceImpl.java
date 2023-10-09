package com.bim.inventory.service.Impl;



import com.bim.inventory.entity.FileEntity;
import com.bim.inventory.repository.FileRepository;
import com.bim.inventory.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

//    public FileServiceImpl(FaylRepository faylRepository) {
//        this.faylRepository = faylRepository;
//    }

    @Override
    public List<FileEntity> getAll(String key) {
        return fileRepository.findAll();
    }

    @Override
    public FileEntity getById(Long id) {
        return fileRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
    }

    @Override
    public FileEntity create(FileEntity entity) {
        if(entity.getId() == null)
        return fileRepository.save(entity);
        throw new RuntimeException("id must be null");
    }

    @Override
    public FileEntity update(FileEntity entity) {
        if(entity.getId() != null)
            return fileRepository.save(entity);
        throw new RuntimeException("id must not be null");
    }

    @Override
    public void delete(FileEntity entity) {
        fileRepository.delete(entity);
    }

    @Override
    public void deleteById(Long dataId) {
        fileRepository.deleteById(dataId);
    }
}
