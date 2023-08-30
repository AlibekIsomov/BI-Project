package com.bim.inventory.service;


import com.bim.inventory.dto.UserDTO;
import com.bim.inventory.entity.User;
import com.bim.inventory.vm.UserVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService extends CommonServiceDto<User, UserDTO>{
//    public Optional<User> getByIdEntity(Long id);

//    public List<UserDTO> getAll();

//    UserDTO update(User user);

//    public Optional<UserDTO> getById(Long id);

//    UserDTO create(User user);

//    public void deleteById(Long id);

    public boolean changePassword(UserVM userVM);

    UserDTO getCurrentUser();

    Optional<User> getByUsername(String login);
    
    public Page<User> search(String key, Pageable pageable);
}