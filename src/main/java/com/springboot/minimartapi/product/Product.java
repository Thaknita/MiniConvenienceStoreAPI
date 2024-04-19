package com.springboot.minimartapi.product;

import com.springboot.minimartapi.order.Order;
import com.springboot.minimartapi.order.OrdersProducts;
import com.springboot.minimartapi.product.category.Category;
import com.springboot.minimartapi.transaction.Transaction;
import com.springboot.minimartapi.user.carts.CartItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "cateId", referencedColumnName = "cateId" )
    private Category category;

    private Double price;
    private Long qtyOnHand;

   @OneToMany (mappedBy = "productId")
   private List<Transaction> transactions;

   @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

   @OneToMany(mappedBy = "product")
    private List<OrdersProducts> ordersProducts;


}
