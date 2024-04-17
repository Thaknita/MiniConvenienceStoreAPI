package com.springboot.minimartapi.user;

import com.springboot.minimartapi.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByReference(Cart cart);
    List<CartItem> findCartItemByReference(Cart cart);

    @Query("""
    SELECT c.qty from CartItem as c WHERE c.product = ?1
    """)
    Long qty (Product product);

    @Query("""
    SELECT c.product.productName from CartItem as c WHERE c.product = ?1
    """)
    String productName (Product product);

    @Query("""
    SELECT c.product.price from CartItem  as c WHERE c.product =?1
    """)
    Float price(Product product);
    @Modifying
    void deleteCartItemsByProductAndReference(Product product, Cart cart);

}
