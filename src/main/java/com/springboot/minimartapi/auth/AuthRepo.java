package com.springboot.minimartapi.auth;
import com.springboot.minimartapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface AuthRepo extends JpaRepository<User, Long> {
    @Modifying
    @Query("""
    UPDATE User as u
    SET u.verifyCode = ?2 where u.emailAddress = ?1
    """)
    void updateVerifiedCode (String email, String code);

    Optional<User> findByEmailAddressAndVerifyCode (String emailAddress, String verifyCode);

}
