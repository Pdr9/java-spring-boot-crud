package com.example.favoriteproducts.mapper;

import com.example.favoriteproducts.dto.ProductDTO;
import com.example.favoriteproducts.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductDTO toDTO(Product product);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDTO productDTO);
    
    List<ProductDTO> toDTOList(List<Product> products);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntityFromDTO(ProductDTO productDTO, @MappingTarget Product product);
} 