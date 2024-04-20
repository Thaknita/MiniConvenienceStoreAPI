package com.springboot.minimartapi.transaction;
import com.springboot.minimartapi.product.dto.ProductIdDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDateTime;
@Builder
public record TransactionCreationDto(
        @NotNull
        ProductIdDto productId,
        @NotNull
        Long productQty
) {
}
