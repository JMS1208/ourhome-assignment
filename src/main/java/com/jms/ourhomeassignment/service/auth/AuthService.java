package com.jms.ourhomeassignment.service.auth;

import com.jms.ourhomeassignment.data.token.JwtToken;
import com.jms.ourhomeassignment.dto.sign.SignUpRequestDto;

public interface AuthService {

    JwtToken signIn(String userId, String password);

    void signUp(SignUpRequestDto requestDto);

    boolean isValidUser(String userId, String password, String token);

}
