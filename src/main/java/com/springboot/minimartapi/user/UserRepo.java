package com.springboot.minimartapi.user;

import com.springboot.minimartapi.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserRepo extends JpaRepository<User, Integer> {

     Boolean existsByUserName(String userName);

     Boolean existsByEmailAddress(String emailAddress);
     Optional<User> findUserByEmailAddressAndIsActiveAndIsVerified(String emailAddress, Boolean isActive, Boolean isVerified);

}
