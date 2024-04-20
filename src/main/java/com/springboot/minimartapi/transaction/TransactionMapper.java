package com.springboot.minimartapi.transaction;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toTransaction (TransactionCreationDto transactionCreationDto);

    List<TransactionDto> toTransactionDto(List<Transaction> transactions);

}
