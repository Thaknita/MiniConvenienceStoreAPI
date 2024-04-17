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
       SELECT distinct c.product FROM CartItem as c where c.reference =?1
       """)
    List<Product> listCartItems(Cart cart);

    @Query("""
    SELECT SUM(c.qty) AS total_qty
    FROM CartItem c
    WHERE c.product = ?1
    """)
    Long qty(Product product);

    @Query("""
    SELECT DISTINCT c.product.productName from CartItem as c WHERE c.product = ?1
    """)
    String productName (Product product);
    @Query("""
    SELECT DISTINCT c.product.price from CartItem  as c WHERE c.product =?1
    """)
    Float price(Product product);
    @Modifying
    void deleteCartItemsByProductAndReference(Product product, Cart cart);

}
