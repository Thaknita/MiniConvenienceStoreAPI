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

void fromProductEditionDto (ProductEditionDto productEditionDto, @MappingTarget Product product);

Product fromItemDto(ItemDto itemDto);
Product fromProductToAddDto(ProductToAddDto product);

List<ProductDto> toProductDtoList(List<Product> products);

Product toProduct(ProductToRemoveFromCartDto product);

List<Product> fromProductInCartDto(List<ProductInCartDto> listProductInCartDto);





}
