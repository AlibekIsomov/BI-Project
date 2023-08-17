package com.bim.inventory.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommonController<T, D>{
    ResponseEntity<Page<T>> getAll(Pageable pageable) throws Exception;
    ResponseEntity<T> getById(D id) throws Exception;
    ResponseEntity<T> create(T data) throws Exception;
    ResponseEntity<T> update(T data) throws Exception;
    void deleteById(D id);
}
