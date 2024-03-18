package com.springboot.minimartapi.order;

import com.springboot.minimartapi.product.Product;
import com.springboot.minimartapi.user.User;
import com.springboot.minimartapi.util.RandomNumber;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long orderNumber;

    private Float totalPrice;
    private Float vat;
    private Float grandTotal;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
    private Boolean isDelivered;
    private String orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime receivedDate;

    @ManyToMany
    private List<Product> products;

}
