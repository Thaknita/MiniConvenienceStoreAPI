
package com.springboot.minimartapi.user.payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;
public interface PaymentRepo extends JpaRepository<PaymentInformation, Long> {

    @Modifying
    void deleteByCardNumber(Long cardNumber);
    Boolean existsByCardNumber(Long cardNumber);
    @Query(""" 
    SELECT u.paymentInformation FROM User as u WHERE u.userId=?1
            """)
    Set<PaymentInformation> findByUserId(Long id);
    Optional<PaymentInformation> findPaymentInformationByCardNumber(Long cardNumber);








}
