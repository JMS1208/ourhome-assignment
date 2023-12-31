package com.jms.ourhomeassignment.controller.auth;

import com.jms.ourhomeassignment.data.token.JwtToken;
import com.jms.ourhomeassignment.data.token.JwtTokens;
import com.jms.ourhomeassignment.dto.ResponseDto;
import com.jms.ourhomeassignment.dto.sign.SignInRequestDto;
import com.jms.ourhomeassignment.dto.sign.SignUpRequestDto;
import com.jms.ourhomeassignment.dto.token.RefreshTokenRequestDto;
import com.jms.ourhomeassignment.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    //로그인 -> 토큰 반환
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto requestDto) {
        ResponseDto<JwtTokens> responseDto = new ResponseDto<>();

        //로그인하면 토큰 발급
        JwtTokens jwtTokens = authService.signIn(requestDto.getId(), requestDto.getPw());

        responseDto.setResult(jwtTokens);
        responseDto.setRtnCd(HttpStatus.OK.value());
        responseDto.setRtnMsg("정상 처리 완료");

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

    //회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        authService.signUp(requestDto);

        responseDto.setRtnCd(HttpStatus.OK.value());
        responseDto.setRtnMsg("정상 처리 완료");
        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

    //토큰 갱신
    @PostMapping("/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto requestDto) {
        ResponseDto<JwtToken> responseDto = new ResponseDto<>();

        JwtToken newAccessToken = authService.recreateAccessToken(requestDto.getAccessToken(), requestDto.getRefreshToken());

        responseDto.setResult(newAccessToken);
        responseDto.setRtnCd(HttpStatus.OK.value());
        responseDto.setRtnMsg("토큰 갱신 완료");
        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

}
