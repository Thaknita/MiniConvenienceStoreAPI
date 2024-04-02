package com.springboot.minimartapi.admin;

import com.springboot.minimartapi.product.CategoryCreationDto;
import com.springboot.minimartapi.product.ProductCreationDto;
import com.springboot.minimartapi.product.ProductEditionDto;
import com.springboot.minimartapi.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
  private final ProductService productService;

  @PostMapping("/products/create")
  void  creatProduct(@Valid @RequestBody ProductCreationDto productCreationDto){
      productService.createProduct(productCreationDto);
  }

    @PatchMapping("/products/edit/{productId}")
    void  editProduct( @RequestBody ProductEditionDto productEditionDto, @PathVariable Long productId){
      productService.editProduct(productEditionDto, productId);
    }

    @DeleteMapping("/products/delete/{productId}")
    void deleteProduct(@PathVariable Long productId){
      productService.deleteProduct(productId);
    }

    @PostMapping("/categories/create")
    void  createCategory(@Valid @RequestBody CategoryCreationDto categoryCreationDto)  {
      productService.createCategory(categoryCreationDto);
    }














}
