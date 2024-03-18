package com.springboot.minimartapi.payment;

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
    private Long cardNumber;
    private String cardType;
    private String DateMonthExp;
    private Integer cvc;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

}
