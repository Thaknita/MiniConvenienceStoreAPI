package com.springboot.minimartapi.user.carts;

import com.springboot.minimartapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {

   Cart findCartByUserId(User userId);
    Boolean existsByUserId(User userId);






}
