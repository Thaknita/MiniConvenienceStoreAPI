

package com.springboot.minimartapi.user.payment;


import com.springboot.minimartapi.user.payment.dto.PaymentCreationDto;
import com.springboot.minimartapi.user.payment.dto.PaymentEditionDto;
import com.springboot.minimartapi.user.payment.dto.PaymentInfoDto;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PaymentMapper {



    PaymentInformation fromPaymentCreationDto(PaymentCreationDto paymentCreationDto);
    Set<PaymentInfoDto> fromPaymentInformation (Set<PaymentInformation> paymentInformation);
    void fromPaymentEditionDto (@MappingTarget PaymentInformation paymentInformation, PaymentEditionDto paymentEditionDto);



}
