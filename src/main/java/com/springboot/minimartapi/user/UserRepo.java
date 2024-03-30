package com.springboot.minimartapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer> {

     Boolean existsByUserName(String userName);
     Optional<User> findUserByUserId(Long userId);
     Boolean existsByEmailAddress(String emailAddress);
     Optional<User> findUserByEmailAddressAndIsActiveAndIsVerified(String emailAddress, Boolean isActive, Boolean isVerified);
     @Modifying
     @Query ("""
               UPDATE User as u
               SET u.deliveryAddress = ?1
               WHERE u.userId = ?2
""")
     void updateDeliveryAddressByUserId (String deliveryAddress, Long id);
}
