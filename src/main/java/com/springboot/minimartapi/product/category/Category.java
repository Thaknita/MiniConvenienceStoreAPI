package com.springboot.minimartapi.product.category;


import com.springboot.minimartapi.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cateId;
    private String cateName;

  @OneToMany (mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Product> products;

}
