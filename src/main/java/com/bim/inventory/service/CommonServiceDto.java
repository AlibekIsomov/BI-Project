package com.bim.inventory.service;


import com.bim.inventory.dto.BaseDTO;
import com.bim.inventory.entity.DistributedEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonServiceDto<ENTITY extends DistributedEntity, DTO extends BaseDTO>{
    public Page<DTO> getAll(Pageable pageable);
    public DTO create(ENTITY entity) throws Exception;
    public DTO update (ENTITY entity);
    public DTO getById(Long id);
    public boolean delete(Long id);
}
