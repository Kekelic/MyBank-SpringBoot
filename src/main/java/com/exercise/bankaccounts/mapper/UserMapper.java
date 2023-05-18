package com.exercise.bankaccounts.mapper;


import com.exercise.bankaccounts.dto.UserDTO;
import com.exercise.bankaccounts.persistance.model.User;
import com.exercise.bankaccounts.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO modelToDTO(User user);

    User createRequestToModel(UserCreateRequest userCreateRequest);

    User DTOToModel(UserDTO userDTO);
}
