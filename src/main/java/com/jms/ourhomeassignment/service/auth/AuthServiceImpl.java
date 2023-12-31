package com.jms.ourhomeassignment.service.auth;

import com.jms.ourhomeassignment.component.jwt.JwtProvider;
import com.jms.ourhomeassignment.data.token.JwtToken;
import com.jms.ourhomeassignment.dto.sign.SignUpRequestDto;
import com.jms.ourhomeassignment.entity.user.User;
import com.jms.ourhomeassignment.exception.exception.AlreadyUserExistedException;
import com.jms.ourhomeassignment.exception.exception.UserNotFoundException;
import com.jms.ourhomeassignment.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);


    private final JwtProvider jwtProvider;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserJpaRepository userJpaRepository;

    @Override
    public JwtToken signIn(String userId, String password) throws UserNotFoundException {
        LOGGER.info("[signIn] 로그인 시도 userId: {}", userId);

        //userId로 User 가져옴 - 없으면 예외
        User user = userJpaRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("user not found"));

        //매개변수로 받은 패스워드 해싱한 값과 데이터베이스에 저장된 해싱된 패스워드와 비교 - 불일치시에 예외 던짐
        if(!isValidPassword(userId, password)) throw new UserNotFoundException("user not found");

        return  jwtProvider.createToken(user.getUserId(), user.getRoles(), JwtProvider.TOKEN_DURATION);
    }

    @Override
    public void signUp(SignUpRequestDto requestDto) throws AlreadyUserExistedException {

        //이미 존재하는 userId 인지 확인 - 존재하면 예외 던짐
        Optional<User> optionalUser = userJpaRepository.findByUserId(requestDto.getId());

        if (optionalUser.isPresent()) throw new AlreadyUserExistedException("이미 존재하는 아이디 입니다.");

        //가입된 userId가 아니라면 패스워드 해싱해서 저장
        User user = User.builder()
                .userId(requestDto.getId())
                .password(passwordEncoder.encode(requestDto.getPw()))
                .roles(List.of("ROLE_USER"))
                .build();

        userJpaRepository.save(user);
    }

    @Override
    public boolean isValidUser(String userId, String password, String token) {

        //토큰이 유효한지 검사
        if (!jwtProvider.validateToken(token)) {
            return false;
        }

        //토큰에서 추출한 userId와 비교 검사
        String userIdFromToken = jwtProvider.getUserId(token);
        if (!userId.equals(userIdFromToken)) {
            return false;
        }

        //패스워드가 일치하는지 검사
        return isValidPassword(userId, password);
    }

    //비밀번호가 유효한지 검사
    private boolean isValidPassword(String userId, String password) throws UserNotFoundException {
        //유저 가져와서
        User user = userJpaRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("not valid user"));
        //해싱한 비밀번호와 비교
        return passwordEncoder.matches(password, user.getPassword());
    }
}
