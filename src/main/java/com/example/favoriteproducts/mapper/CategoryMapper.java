package com.example.favoriteproducts.mapper;

import com.example.favoriteproducts.dto.CategoryDTO;
import com.example.favoriteproducts.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    
    CategoryDTO toDTO(Category category);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);
    
    List<CategoryDTO> toDTOList(List<Category> categories);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntityFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
} 