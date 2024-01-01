package com.jms.ourhomeassignment.config.security;

import com.jms.ourhomeassignment.component.jwt.JwtProvider;
import com.jms.ourhomeassignment.exception.handler.CustomAccessDeniedHandler;
import com.jms.ourhomeassignment.exception.handler.CustomAuthenticationEntryPoint;
import com.jms.ourhomeassignment.filterchain.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {"/public/**"};

    private final JwtProvider jwtProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Rest API 에서는 필요없어서 CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        //Rest API 기반의 애플리케이션 동작 방식을 설정
        //세션 방식은 쓰지 않기 때문에 STATELESS
        http.sessionManagement(auth -> auth
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        //api 접근 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
        );

        //권한 확인 과정에서 통과하지 못하는 예외가 발생할 때, 예외를 전달
        http.exceptionHandling(auth -> auth
                .accessDeniedHandler(new CustomAccessDeniedHandler())
        );

        //인증 과정에서 예외가 발생할 경우 예외를 전달
        http.exceptionHandling(auth -> auth
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        );

        //사용자 이름 비밀번호 인증 이전에 JWT 기반 인증 수행
        //UsernamePasswordAuthenticationFilter 보다 우선적으로 적용되게끔 (폼 로그인 방식이 아니기 때문에)
        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    //https 리디렉션
    @Bean
    public ServletWebServerFactory servletContainer() {
        // Enable SSL Trafic
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        // Http -> Https 리디렉션
        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

        return tomcat;
    }

    //Connector는 네트워크 연결을 처리하는 엔드포인트(end-point)로서의 역할
    private Connector httpToHttpsRedirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(443);
        return connector;
    }
}
