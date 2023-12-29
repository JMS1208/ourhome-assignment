package com.jms.ourhomeassignment.component.jwt;

import com.jms.ourhomeassignment.data.token.JwtToken;
import com.jms.ourhomeassignment.data.token.JwtTokens;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface JwtProvider {

    //아이디와 권한으로 액세스 토큰 생성
    JwtToken createToken(String userId, List<String> roles, long duration);

    //액세스 토큰, 리프레시 토큰 갱신
    JwtTokens refreshJwtTokens(String accessToken, String refreshToken);

    //토큰으로 인증정보 조회
    Authentication getAuthentication(String token);

    //토큰으로 유저 아이디 조회
    String getUserId(String token);

    //토큰으로 권한 조회
    List<String> getRoles(String token);

    //Http 헤더에서 액세스 토큰 추출
    String resolveToken(HttpServletRequest request);

    boolean validateToken(String token);
}
