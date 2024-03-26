

package com.springboot.minimartapi.user;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentInformation fromPaymentCreationDto(PaymentCreationDto paymentCreationDto);

}
