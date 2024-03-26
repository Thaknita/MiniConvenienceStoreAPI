package com.springboot.minimartapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer> {

     Boolean existsByUserName(String userName);
     Optional<User> findUserByUserId(Long userId);
     Boolean existsByEmailAddress(String emailAddress);
     Optional<User> findUserByEmailAddressAndIsActiveAndIsVerified(String emailAddress, Boolean isActive, Boolean isVerified);

}
