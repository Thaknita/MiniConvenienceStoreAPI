package com.springboot.minimartapi.transaction;

import com.springboot.minimartapi.product.Product;
import com.springboot.minimartapi.product.dto.ProductDto;
import com.springboot.minimartapi.product.dto.ProductIdDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionDto(
        Long transactionId,
        ProductIdDto productId,
        String transactionType,
        Long productQty,
        LocalDateTime transactionDate

) {
}
