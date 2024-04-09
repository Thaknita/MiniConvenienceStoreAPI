package com.springboot.minimartapi.user.cart;

import com.springboot.minimartapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart , Long> {

    Cart findByUserId(User userId);

    Boolean existsByUserId(User userId);

}
