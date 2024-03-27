
package com.springboot.minimartapi.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;
public interface PaymentRepo extends JpaRepository<PaymentInformation, Long> {
    Boolean existsByCardNumber(Long cardNumber);
    @Query(""" 
    SELECT u.paymentInformation FROM User as u WHERE u.userId=?1
            """)
    Set<PaymentInformation> findByUserId(Long id);

}
