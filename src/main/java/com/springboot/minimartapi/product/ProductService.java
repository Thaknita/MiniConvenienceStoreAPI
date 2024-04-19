package com.springboot.minimartapi.product;

import com.springboot.minimartapi.product.category.CategoryCreationDto;
import com.springboot.minimartapi.product.dto.ProductCreationDto;
import com.springboot.minimartapi.product.dto.ProductDto;
import com.springboot.minimartapi.product.dto.ProductEditionDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> search(String productName, String productDescription);

    List<ProductDto> listAllProducts();

    List<ProductDto>  listByCateId(Integer cateId);

    void createProduct(ProductCreationDto productCreationDto);

    void editProduct(ProductEditionDto productEditionDto, Long productId);

    void deleteProduct(Long productId);

    void createCategory(CategoryCreationDto categoryCreationDto);



}
