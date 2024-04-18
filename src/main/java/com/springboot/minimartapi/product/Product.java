package com.springboot.minimartapi.product;

import com.springboot.minimartapi.order.Order;
import com.springboot.minimartapi.transaction.Transaction;
import com.springboot.minimartapi.user.carts.CartItem;
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
    private Long productId;

    private String productName;
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "cateId", referencedColumnName = "cateId" )
    private Category category;

    private Double price;
    private Long qtyOnHand;

   @ManyToMany (mappedBy = "products")
    private List<Order> orders;

   @OneToMany (mappedBy = "productId")
   private List<Transaction> transactions;

   @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;


}
