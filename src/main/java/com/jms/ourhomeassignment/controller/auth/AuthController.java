package com.jms.ourhomeassignment.controller.auth;

import com.jms.ourhomeassignment.data.token.JwtTokens;
import com.jms.ourhomeassignment.dto.ResponseDto;
import com.jms.ourhomeassignment.dto.sign.SignInRequestDto;
import com.jms.ourhomeassignment.dto.sign.SignUpRequestDto;
import com.jms.ourhomeassignment.dto.token.RefreshTokenRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/auth")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto requestDto) {
        ResponseDto<JwtTokens> responseDto = new ResponseDto<>();
        responseDto.setRtnCd(HttpStatus.OK.value());
        responseDto.setRtnMsg("정상 처리 완료 " + requestDto.toString());

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setRtnCd(HttpStatus.OK.value());
        responseDto.setRtnMsg("정상 처리 완료 " + requestDto.toString());
        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto requestDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setRtnCd(HttpStatus.OK.value());
        responseDto.setRtnMsg("정상 처리 완료 " + requestDto.toString());
        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }


}
