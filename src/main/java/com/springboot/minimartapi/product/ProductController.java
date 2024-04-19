package com.springboot.minimartapi.product;

import com.springboot.minimartapi.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{category_id}")
    List<ProductDto> listByCategory(@PathVariable Integer category_id){
        return productService.listByCateId(category_id);
    }

}
