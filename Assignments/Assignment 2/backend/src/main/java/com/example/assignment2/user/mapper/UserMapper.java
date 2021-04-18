package com.example.assignment2.user.mapper;

import com.example.assignment2.user.dto.UserDTO;
import com.example.assignment2.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "password", ignore = true)
    })
    User fromDto(UserDTO dto);
}

