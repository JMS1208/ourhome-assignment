package com.jms.ourhomeassignment.data.token;

import lombok.Data;

@Data
public class JwtTokens {
    private JwtToken accessToken;
    private JwtToken refreshToken;
}
