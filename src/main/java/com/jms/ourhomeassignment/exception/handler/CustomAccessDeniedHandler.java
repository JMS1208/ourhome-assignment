package com.jms.ourhomeassignment.exception.handler;

import com.jms.ourhomeassignment.dto.ResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


//접근 권한이 없는 리소스에 접근하는 경우 던지는 AccessDeniedException 핸들러
//403 - Forbidden
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setRtnCd(HttpStatus.FORBIDDEN.value());
        responseDto.setRtnMsg("접근 권한이 없습니다.");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseDto.toJsonString());
    }
}
