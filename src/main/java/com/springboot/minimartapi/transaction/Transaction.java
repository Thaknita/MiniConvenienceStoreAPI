package com.springboot.minimartapi.transaction;

import com.springboot.minimartapi.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @ManyToOne

    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product productId;
    private String transactionType;
    private Long productQty;
    private LocalDateTime transactionDate;

}
