package com.springboot.minimartapi.product;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
List<ProductDto> toProductDto (List<Product> products);
Product fromProductCreationDto (ProductCreationDto productCreationDto);

void fromProductEditionDto ( ProductEditionDto productEditionDto,@MappingTarget Product product);


Product fromProductToAddDto(ProductToAddDto product);

List<ProductDto> toProductDtoList(List<Product> products);

Product toProduct(ProductToRemoveFromCartDto product);



}
