package com.bim.inventory.converter;


import com.bim.inventory.dto.UserDTO;
import com.bim.inventory.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor extends AbstractDTOConverter<User, UserDTO> {
    @Override
    public UserDTO convert(User entity) {
        UserDTO userDTO = new UserDTO(entity);

        super.convert(entity, userDTO);

        return userDTO;
    }
}
