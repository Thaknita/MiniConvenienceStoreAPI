

package com.springboot.minimartapi.payment;


import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PaymentMapper {



    PaymentInformation fromPaymentCreationDto(PaymentCreationDto paymentCreationDto);
    Set<PaymentInfoDto> fromPaymentInformation (Set<PaymentInformation> paymentInformation);
    void fromPaymentEditionDto (@MappingTarget PaymentInformation paymentInformation, PaymentEditionDto paymentEditionDto);



}
