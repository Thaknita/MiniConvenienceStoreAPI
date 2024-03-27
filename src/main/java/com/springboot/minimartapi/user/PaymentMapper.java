

package com.springboot.minimartapi.user;


import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentInformation fromPaymentCreationDto(PaymentCreationDto paymentCreationDto);


    Set<PaymentInfoDto> fromPaymentInformation (Set<PaymentInformation> paymentInformation);


}
