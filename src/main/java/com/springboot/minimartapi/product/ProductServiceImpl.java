package com.springboot.minimartapi.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public List<ProductDto> search(String productName, String productDescription) {


        return null;
    }
}
