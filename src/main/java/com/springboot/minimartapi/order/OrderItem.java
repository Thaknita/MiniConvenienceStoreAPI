package com.springboot.minimartapi.order;

import com.springboot.minimartapi.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oder_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oderItemId;

    private Long qty;

    @ManyToOne
    @JoinColumn(name = "orderNumber", referencedColumnName = "orderNumber")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product;


}
