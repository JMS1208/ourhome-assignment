package com.jms.ourhomeassignment.dto.token;

import lombok.Data;

@Data
public class RefreshTokenRequestDto {
    private String refreshToken;
    private String accessToken;
}
