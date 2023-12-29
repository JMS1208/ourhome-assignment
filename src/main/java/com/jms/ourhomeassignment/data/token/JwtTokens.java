package com.jms.ourhomeassignment.data.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokens {
    private JwtToken accessToken;
    private JwtToken refreshToken;
}
