package com.springboot.minimartapi.product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepo categoryRepo;
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

    @Override
    public List<ProductDto> listByCateId(Integer cateId) {
        return  productMapper.toProductDto(productRepo.findAllByCategoryId(cateId));
    }

    @Override
    public void createProduct(ProductCreationDto productCreationDto) {

        Product product = productMapper.fromProductCreationDto(productCreationDto);

        productRepo.save(product);

    }

    @Override
    public void editProduct(ProductEditionDto productEditionDto, Long productId) {

        Product product = productRepo.findProductById(productId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not exist")
        );

        product.setProductName(Optional.ofNullable(productEditionDto.productName()).orElse(product.getProductName()));
        product.setProductDescription(Optional.ofNullable(productEditionDto.productDescription()).orElse(product.getProductDescription()));
        product.setCategory(Optional.ofNullable(productEditionDto.category()).orElse(product.getCategory()));
        product.setPrice(Optional.ofNullable(productEditionDto.price()).orElse(product.getPrice()));

        productRepo.save(product);
    }
    @Transactional
    @Override
    public void deleteProduct(Long productId) {

      productRepo.findProductById(productId).orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist")
      );

      productRepo.deleteProductById(productId);

    }

    @Override
    public void createCategory(CategoryCreationDto categoryCreationDto) {

        Category category = categoryMapper.fromCategoryCreationDto(categoryCreationDto);

        categoryRepo.save(category);

    }

}


