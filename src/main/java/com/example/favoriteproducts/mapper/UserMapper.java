package com.example.favoriteproducts.mapper;

import com.example.favoriteproducts.dto.UserDTO;
import com.example.favoriteproducts.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    @Mapping(source = "products", target = "products")
    UserDTO toDTO(User user);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    User toEntity(UserDTO userDTO);
    
    List<UserDTO> toDTOList(List<User> users);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntityFromDTO(UserDTO userDTO, @MappingTarget User user);
} 