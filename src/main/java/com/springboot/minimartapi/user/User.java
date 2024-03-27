package com.springboot.minimartapi.user;

import com.springboot.minimartapi.order.Order;
import com.springboot.minimartapi.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true, nullable = false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String emailAddress;
    private String password;
    private String verifyCode;
    private Boolean isActive;
    private Boolean isVerified;
    private String deliveryAddress;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PaymentInformation> paymentInformation ;

    @OneToMany (mappedBy = "user")
    private List<Order> orders;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
           name = "users_roles",
           joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Set<Role> roles;

}
