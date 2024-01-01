package com.jms.ourhomeassignment.service.auth;

import com.jms.ourhomeassignment.data.token.JwtToken;
import com.jms.ourhomeassignment.data.token.JwtTokens;
import com.jms.ourhomeassignment.dto.sign.SignUpRequestDto;

import java.util.List;

public interface AuthService {

    JwtTokens signIn(String userId, String password);

    JwtTokens createTokens(String userId, List<String> roles);

    JwtToken recreateAccessToken(String accessToken, String refreshToken);

    void signUp(SignUpRequestDto requestDto);

    boolean isValidUser(String userId, String password, String token);

    String getCurrentUserId();

}
