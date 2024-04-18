package com.springboot.minimartapi.user.carts;

import com.springboot.minimartapi.user.User;
import lombok.Builder;

@Builder
public record CartDto(
        User userId

) {
}
