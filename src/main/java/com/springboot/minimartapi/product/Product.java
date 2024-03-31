package com.springboot.minimartapi.product;

import com.springboot.minimartapi.order.Order;
import com.springboot.minimartapi.transaction.Transaction;
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
    private Integer categoryId;
    private Float price;
    private Long qtyOnHand;

   @ManyToMany (mappedBy = "products")
    private List<Order> orders;

   @OneToMany (mappedBy = "productId")
   private List<Transaction> transactions;

}
