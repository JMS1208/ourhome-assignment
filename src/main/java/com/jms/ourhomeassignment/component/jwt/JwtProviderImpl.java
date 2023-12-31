package com.jms.ourhomeassignment.component.jwt;

import com.jms.ourhomeassignment.data.token.JwtToken;
import com.jms.ourhomeassignment.exception.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtProviderImpl implements JwtProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtProviderImpl.class);

    private final UserDetailsService userDetailsService;


    @Value("${jwt.secret}")
    private String secretKey;

    private Claims createClaims(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);
        return claims;
    }

    @Override
    public JwtToken createToken(String userId, List<String> roles, long duration) {
        LOGGER.info("[createToken] 토큰 생성 시작");

        Claims claims = createClaims(userId, roles);

        Date now = new Date();

        Date expiredAt = new Date(now.getTime() + duration);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");

        return new JwtToken(token, expiredAt);
    }

    @Override
    public JwtToken recreateToken(String accessToken, String refreshToken) throws InvalidTokenException {

        //refreshToken 이 만료되었는 지 확인
        if(!validateToken(refreshToken)) throw new InvalidTokenException("리프레시 토큰 만료");

        //refreshToken 과 accessToken 의 아이디가 같은지 검증
        String refreshTokenUserId = getUserId(refreshToken);
        String accessTokenUserId = getUserId(accessToken);
        if(!refreshTokenUserId.equals(accessTokenUserId)) throw new InvalidTokenException("토큰 불일치");

        List<String> roles = getRoles(refreshToken);

        return createToken(refreshTokenUserId, roles, JwtProvider.ACCESS_TOKEN_DURATION);
    }

    @Override
    public Authentication getAuthentication(String token) {
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));

        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserEmail: {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String getUserId(String token) throws InvalidTokenException {
        try {
            LOGGER.info("[getUserId] 토큰 기반 회원 구별 정보 추출");
            String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                    .getSubject();

            LOGGER.info("[getUserId] 토큰 기반 회원 구별 정보 추출 완료, info: {}", info);

            return info;
        } catch (Exception e) {
            throw new InvalidTokenException("토큰이 유효하지 않습니다.");
        }
    }

    @Override
    public List<String> getRoles(String token) throws InvalidTokenException {

        try {
            LOGGER.info("[getRoles] 토큰 기반 사용자 권한 정보 추출");

            // 토큰 파싱
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

            // 권한 정보 추출
            List<String> roles = claims.get("roles", List.class);

            LOGGER.info("[getRoles] 토큰 기반 사용자 권한 정보 추출 완료, roles: {}", roles);

            return roles;
        } catch (Exception e) {
            throw new InvalidTokenException("토큰이 유효하지 않습니다.");
        }

    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] Http Header에서 Token 추출");

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 다음의 토큰 부분을 추출
        }
        return null;
    }

    //유효성, 만료 기간까지 확인
    @Override
    public boolean validateToken(String token) {
        LOGGER.info("[validateToken] 토큰 유효성 체크");
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            //만료기간 안 지났는지 확인
            return true;
        } catch(Exception e) {
            LOGGER.info("[validateToken] 토큰 유효 체크 예외");
            return false;
        }
    }
}
