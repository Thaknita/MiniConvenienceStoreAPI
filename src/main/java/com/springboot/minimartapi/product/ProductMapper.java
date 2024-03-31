package com.springboot.minimartapi.product;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
List<ProductDto> toProductDto (List<Product> products);


}
