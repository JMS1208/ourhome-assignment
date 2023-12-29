package com.jms.ourhomeassignment.exception.handler;

import com.jms.ourhomeassignment.dto.ResponseDto;
import com.jms.ourhomeassignment.exception.exception.AlreadyUserExistedException;
import com.jms.ourhomeassignment.exception.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setRtnCd(HttpStatus.BAD_REQUEST.value());
        responseDto.setRtnMsg("아이디나 비밀번호를 확인해주세요.");

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

    @ExceptionHandler(AlreadyUserExistedException.class)
    public ResponseEntity<?> handleAlreadyUserExistedException(AlreadyUserExistedException e) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setRtnCd(HttpStatus.BAD_REQUEST.value());
        responseDto.setRtnMsg("이미 존재하는 아이디입니다.");

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }
}
