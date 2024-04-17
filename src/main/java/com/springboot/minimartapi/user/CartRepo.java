package com.springboot.minimartapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {

   Cart findCartByUserId(User userId);
    Boolean existsByUserId(User userId);






}
