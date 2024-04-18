package com.springboot.minimartapi.user.carts;

import com.springboot.minimartapi.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reference;

   @OneToOne
   @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User userId;

    @OneToMany(mappedBy = "reference")
    private List<CartItem> cartItem;



}
