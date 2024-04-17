package com.springboot.minimartapi.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByProductDescriptionContainingIgnoreCase(String productDescription);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findAllByProductIdIsNotNull();
    @Query("""
    SELECT p FROM Product as p where p.category.cateId=?1
""")
    List<Product> findAllByCategoryId(Integer categoryId);
    Optional<Product> findProductByProductId(Long productId);
    @Modifying
    void deleteProductByProductId(Long productId);

    @Query("""
        SELECT p.qtyOnHand from Product as p WHERE p.productId =?1
    """)
    Long qtyOnHand(Long productId);





}
