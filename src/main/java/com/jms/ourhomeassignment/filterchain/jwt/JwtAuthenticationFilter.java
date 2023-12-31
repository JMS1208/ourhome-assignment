package com.jms.ourhomeassignment.filterchain.jwt;

import com.jms.ourhomeassignment.component.jwt.JwtProvider;
import com.jms.ourhomeassignment.config.security.SecurityConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();

        LOGGER.info("[doFilterInternal] 요청 URI: {}", requestURI);

        if(Arrays.stream(SecurityConfig.PUBLIC_URLS).noneMatch(url -> pathMatcher.match(url, requestURI))) {
            String token = jwtProvider.resolveToken(request);

            LOGGER.info("[doFilterInternal] token 값 추출 완료. token: {}", token);
            LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");

            if(token != null && jwtProvider.validateToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
            }
        }

        filterChain.doFilter(request, response);
    }
}
