
package com.springboot.minimartapi.user;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentRepo extends JpaRepository<PaymentInformation, Long> {
    Boolean existsByCardNumber(Long cardNumber);
}
