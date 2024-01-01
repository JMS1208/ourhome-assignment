package com.jms.ourhomeassignment.component.jwt;

import com.jms.ourhomeassignment.data.token.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface JwtProvider {

    //액세스 토큰 유효기간 1시간 -> 테스트를 위해 7일로 수정
    public static final long ACCESS_TOKEN_DURATION = 1000L * 60 * 60 * 24 * 7;

    //리프레시 토큰 유효기간 30일
    public static final long REFRESH_TOKEN_DURATION = 1000L * 60 * 60 * 24 * 30;

    //아이디와 권한으로 액세스 토큰 생성
    JwtToken createToken(String userId, List<String> roles, long duration);

    //액세스 토큰 갱신
    JwtToken recreateToken(String accessToken, String refreshToken);

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
