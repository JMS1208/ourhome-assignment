package com.jms.ourhomeassignment.controller.order;

import com.jms.ourhomeassignment.component.jwt.JwtProvider;
import com.jms.ourhomeassignment.data.order.history.OrderHistory;
import com.jms.ourhomeassignment.dto.RequestDto;
import com.jms.ourhomeassignment.dto.ResponseDto;
import com.jms.ourhomeassignment.dto.order.OrderHistoryDto;
import com.jms.ourhomeassignment.dto.query.SearchRequestDto;
import com.jms.ourhomeassignment.service.auth.AuthService;
import com.jms.ourhomeassignment.service.order.history.OrderHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderHistoryController {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryController.class);

    private final JwtProvider jwtProvider;

    private final AuthService authService;

    private final OrderHistoryService orderHistoryService;

    private final int PAGE_SIZE = 10;
    private final int PAGE = 0;

    /**
     * 동적인 쿼리 파라미터
     */
    @GetMapping("/api/orders")
    public ResponseEntity<?> searchWithHeaderToken(
        HttpServletRequest httpServletRequest
    ) {
        ResponseDto<OrderHistoryDto> responseDto = new ResponseDto<>();

        String token = jwtProvider.resolveToken(httpServletRequest);
        String userId = jwtProvider.getUserId(token);

        OrderHistory orderHistory = orderHistoryService.getOrderHistory(userId, PageRequest.of(PAGE, PAGE_SIZE));

        responseDto.setResult(OrderHistoryDto.from(orderHistory));
        responseDto.setRtnMsg("select success");
        responseDto.setRtnCd(HttpStatus.OK.value());

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

    /**
     * 동적인 쿼리 파라미터
     */
    @PostMapping("/public/orders")
    public ResponseEntity<?> searchWithoutHeaderToken(
            @RequestBody RequestDto<SearchRequestDto> requestDto
    ) {
        ResponseDto<OrderHistoryDto> responseDto = new ResponseDto<>();

        boolean isValid = authService.isValidUser(requestDto.getBody().getId(), requestDto.getBody().getPw(), requestDto.getToken());

        //요청한 사람이 유효하지 않은 경우
        if(!isValid) {
            responseDto.setRtnMsg("not valid user");
            responseDto.setRtnCd(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
        }

        LOGGER.info("[searchWithoutHeaderToken] 검색 ID: {}", requestDto.getBody().getId());

        OrderHistory orderHistory = orderHistoryService.getOrderHistory(requestDto.getBody().getId(), PageRequest.of(PAGE, PAGE_SIZE));

        LOGGER.info("[searchWithoutHeaderToken] 결과: {}", OrderHistoryDto.from(orderHistory));

        responseDto.setResult(OrderHistoryDto.from(orderHistory));
        responseDto.setRtnMsg("select success");
        responseDto.setRtnCd(HttpStatus.OK.value());

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }
}
