package com.springboot.minimartapi.product;

import java.util.List;

public interface ProductService {

    List<ProductDto> search(String productName, String productDescription);

    List<ProductDto> listAllProducts();

}
