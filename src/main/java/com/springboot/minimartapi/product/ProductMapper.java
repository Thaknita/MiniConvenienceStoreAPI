package com.springboot.minimartapi.product;

import com.springboot.minimartapi.product.dto.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
List<ProductDto> toProductDto (List<Product> products);
Product fromProductCreationDto (ProductCreationDto productCreationDto);
Product toProduct(ProductToRemoveFromCartDto product);

}
