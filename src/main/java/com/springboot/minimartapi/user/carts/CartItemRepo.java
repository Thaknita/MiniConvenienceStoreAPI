package com.springboot.minimartapi.user.carts;

import com.springboot.minimartapi.product.Product;
import com.springboot.minimartapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    @Query("""
       SELECT distinct c.product FROM CartItem as c where c.reference =?1
       """)
    List<Product> listCartItems(Cart cart);

    @Query("""
    SELECT SUM(c.qty) AS total_qty
    FROM CartItem c
    WHERE c.product = ?1 AND c.reference = ?2
    """)
    Long qty(Product product, Cart cart);

    @Query("""
    SELECT SUM(c.product.price) AS total_price
    FROM CartItem c
    WHERE c.reference = ?1
    """)
    Double totalPrice(Cart cart);

    @Query("""
    SELECT DISTINCT c.product.productName from CartItem as c WHERE c.product = ?1
    """)
    String productName (Product product);
    @Query("""
    SELECT DISTINCT c.product.price from CartItem  as c WHERE c.product =?1
    """)
    Double price(Product product);
    @Modifying
    void deleteCartItemsByProductAndReference(Product product, Cart cart);
    @Modifying
    @Query("""
        DELETE FROM CartItem as c WHERE c.reference = ?1
    """)
    void cleanCart(Cart cart);

}
