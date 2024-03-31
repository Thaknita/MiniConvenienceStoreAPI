package com.springboot.minimartapi.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    @Override
    public List<ProductDto> search(String productName, String productDescription) {

        List<Product> products = new ArrayList<>();

        if (!productName.isBlank()){
            List<Product> productByName = productRepo.findByProductNameContainingIgnoreCase(productName);
            products.addAll(productByName);
        }
        if (!productDescription.isBlank()){
            List<Product> productByDescription = productRepo.findByProductDescriptionContainingIgnoreCase(productDescription);
            products.addAll(productByDescription);
        }
        return productMapper.toProductDto(products);
    }

    @Override
    public List<ProductDto> listAllProducts() {
        return productMapper.toProductDto(productRepo.findAllByIdIsNotNull());
    }
}
