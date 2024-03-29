package com.springboot.minimartapi.payment;

import com.springboot.minimartapi.user.User;
import com.springboot.minimartapi.user.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
