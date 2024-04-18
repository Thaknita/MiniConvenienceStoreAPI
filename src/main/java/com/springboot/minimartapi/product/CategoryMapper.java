package com.springboot.minimartapi.product;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category fromCategoryCreationDto(CategoryCreationDto categoryCreationDto);

    Category fromCateDto(CategoryDto categoryDto);
}
