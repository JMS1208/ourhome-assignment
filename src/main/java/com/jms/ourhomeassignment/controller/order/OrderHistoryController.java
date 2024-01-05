package com.jms.ourhomeassignment.controller.order;

import com.jms.ourhomeassignment.data.order.history.OrderHistory;
import com.jms.ourhomeassignment.dto.RequestDto;
import com.jms.ourhomeassignment.dto.ResponseDto;
import com.jms.ourhomeassignment.dto.order.OrderHistoryDto;
import com.jms.ourhomeassignment.dto.query.SearchRequestDto;
import com.jms.ourhomeassignment.service.auth.AuthService;
import com.jms.ourhomeassignment.service.order.history.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderHistoryController {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryController.class);

    private final AuthService authService;

    private final OrderHistoryService orderHistoryService;

    @GetMapping("/api/orders")
    public ResponseEntity<?> searchWithHeaderToken(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "DESC") Sort.Direction direction
    ) {

        OrderHistory orderHistory = orderHistoryService.getOrderHistory(PageRequest.of(page, size, Sort.by(direction, "orderedAt")));

        ResponseDto<OrderHistoryDto> responseDto = new ResponseDto<>();
        responseDto.setResult(OrderHistoryDto.from(orderHistory));
        responseDto.setRtnMsg("select success");
        responseDto.setRtnCd(HttpStatus.OK.value());
        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

    @PostMapping("/public/orders")
    public ResponseEntity<?> searchWithoutHeaderToken(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "DESC") Sort.Direction direction,
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

        OrderHistory orderHistory = orderHistoryService.getOrderHistory(requestDto.getBody().getId(), PageRequest.of(page, size, Sort.by(direction, "orderedAt")));

        LOGGER.info("[searchWithoutHeaderToken] 결과: {}", OrderHistoryDto.from(orderHistory));

        responseDto.setResult(OrderHistoryDto.from(orderHistory));
        responseDto.setRtnMsg("select success");
        responseDto.setRtnCd(HttpStatus.OK.value());

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }
}
