package com.springboot.minimartapi.user.shoppingcart;

import com.springboot.minimartapi.product.Product;
import com.springboot.minimartapi.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reference ;

    @OneToMany (mappedBy = "referenceCart")
    private List<User> userId;

    @ManyToMany
    @JoinTable (name = "cart_products", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "cart_reference"))
    private List<Product> productList;




}
