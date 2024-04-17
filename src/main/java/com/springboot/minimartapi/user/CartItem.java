package com.springboot.minimartapi.user;

import com.springboot.minimartapi.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long qty;

    @ManyToOne
    @JoinColumn(name = "reference", referencedColumnName = "reference")
    private Cart reference;

    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "productId")
    private Product product;

}
