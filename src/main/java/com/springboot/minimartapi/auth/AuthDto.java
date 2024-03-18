package com.springboot.minimartapi.auth;

import lombok.Builder;

@Builder
public record AuthDto(

        String tokenType,
        String accessToken,
        String refreshToken

) {
}
