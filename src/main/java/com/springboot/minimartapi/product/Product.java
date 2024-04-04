package com.springboot.minimartapi.product;

import com.springboot.minimartapi.order.Order;
import com.springboot.minimartapi.transaction.Transaction;
import com.springboot.minimartapi.user.shoppingcart.ShoppingCart;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "cateId", referencedColumnName = "cateId" )
    private Category category;

    private Float price;
    private Long qtyOnHand;

   @ManyToMany (mappedBy = "products")
    private List<Order> orders;

   @OneToMany (mappedBy = "productId")
   private List<Transaction> transactions;

   @ManyToMany (mappedBy = "productList")
   private List<ShoppingCart> shoppingCarts;


}
