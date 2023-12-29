package com.jms.ourhomeassignment.service.auth;

import com.jms.ourhomeassignment.component.jwt.JwtProvider;
import com.jms.ourhomeassignment.data.token.JwtTokens;
import com.jms.ourhomeassignment.exception.exception.AlreadyUserExistedException;
import com.jms.ourhomeassignment.exception.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);


    private final JwtProvider jwtProvider;

    @Override
    public JwtTokens signIn(String userId, String password) throws UserNotFoundException {
        LOGGER.info("[signIn] 로그인 시도 userId: {}", userId);

        //userId로 User 가져옴

        //null 이면 예외

        //매개변수로 받은 패스워드 해싱한 값과 데이터베이스에 저장된 해싱된 패스워드와 비교

        //패스워드 일치시에 토큰 반환 - 불일치시에 예외 던짐


    }

    //TODO - Transactional 고려
    @Override
    public void signUp(String userId, String password) throws AlreadyUserExistedException {

        //이미 존재하는 userId 인지 확인 - 존재하면 예외 던짐

        //가입된 userId가 아니라면 패스워드 해싱해서 저장


    }

    @Override
    public JwtTokens refreshToken(String accessToken, String refreshToken) {
        return jwtProvider.refreshJwtTokens(accessToken, refreshToken);
    }
}
