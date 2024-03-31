package com.springboot.minimartapi.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByProductDescriptionContainingIgnoreCase(String productDescription);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findAllByIdIsNotNull();
    List<Product> findAllByCategoryId(Integer categoryId);

}
