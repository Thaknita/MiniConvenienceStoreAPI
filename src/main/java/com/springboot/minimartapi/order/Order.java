package com.springboot.minimartapi.order;
import com.springboot.minimartapi.product.Product;
import com.springboot.minimartapi.user.User;
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
    private Double totalPrice;
    private Double vat;
    private Double grandTotal;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User userId;
    private Boolean isDelivered;
    private String orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime receivedDate;
    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_number"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

}
