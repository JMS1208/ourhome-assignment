package com.jms.ourhomeassignment.service.auth;

import com.jms.ourhomeassignment.data.token.JwtTokens;

public interface AuthService {

    JwtTokens signIn(String userId, String password);

    void signUp(String userId, String password);

    JwtTokens refreshToken(String accessToken, String refreshToken);
}
