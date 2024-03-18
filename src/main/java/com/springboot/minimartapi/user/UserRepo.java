package com.springboot.minimartapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

     Boolean existsByUserName(String userName);

     Boolean existsByEmailAddress(String emailAddress);
     Optional<User> findUserByEmailAddressAndIsActiveAndIsVerified(String emailAddress, Boolean isActive, Boolean isVerified);

}
