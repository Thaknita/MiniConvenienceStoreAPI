package com.springboot.minimartapi.product;

import com.springboot.minimartapi.order.dto.ItemDto;
import com.springboot.minimartapi.product.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
List<ProductDto> toProductDto (List<Product> products);
Product fromProductCreationDto (ProductCreationDto productCreationDto);
Product toProduct(ProductToRemoveFromCartDto product);







}
