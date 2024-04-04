package com.springboot.minimartapi.user.payment;

import com.springboot.minimartapi.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paymentInfo")
public class PaymentInformation {

    @Id
    @Column(unique = true, nullable = false)
    private Long cardNumber;
    private String cardType;
    private String dateMonthExp;
    private Integer cvc;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

}
