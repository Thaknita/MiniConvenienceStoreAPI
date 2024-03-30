package com.springboot.minimartapi.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    List<ProductDto> search(@RequestParam (required = false, defaultValue = "") String productName,
                            @RequestParam (required = false, defaultValue = "") String productDescription){
        return productService.search(productName, productDescription);
    }




}
