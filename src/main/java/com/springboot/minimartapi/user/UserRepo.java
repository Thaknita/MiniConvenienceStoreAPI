package com.springboot.minimartapi.user;

import com.springboot.minimartapi.admin.role.Role;
import com.springboot.minimartapi.user.payment.PaymentInformation;
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

     @Query("""
     SELECT u.paymentInformation FROM User as u WHERE u.userId =?1
     """)
     PaymentInformation paymentInfo(Long userId);
     @Modifying
     @Query("""
     UPDATE User u
     SET u.isActive = false
     WHERE u.userId =?1
     """)
     void deactivateUser(Long userId);

     void deleteByUserId(Long userId);


}
