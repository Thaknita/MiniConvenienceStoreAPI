package com.springboot.minimartapi.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    List<ProductDto> search(@RequestParam (required = false, defaultValue = "") String productName,
                            @RequestParam (required = false, defaultValue = "") String productDescription){
        return productService.search(productName, productDescription);
    }

    @GetMapping("/list")
    List<ProductDto> listAll(){
        return productService.listAllProducts();
    }




}
