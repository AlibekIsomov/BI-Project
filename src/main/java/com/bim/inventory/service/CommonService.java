package com.bim.inventory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommonService<T, D>{
    Page<T> getAll(Pageable pageable) throws Exception;
    Optional<T> getById(D id) throws Exception;
    Optional<T> create(T data) throws Exception;
    Optional<T> update(T data) throws Exception;
    void deleteById(D id);
}
