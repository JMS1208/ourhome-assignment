package com.jms.ourhomeassignment.controller.query;

import com.jms.ourhomeassignment.data.token.JwtTokens;
import com.jms.ourhomeassignment.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QueryController {

    private final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);

    /**
     * 동적인 쿼리 파라미터
     * */
    @GetMapping("/api/query")
    public ResponseEntity<?> queryWithHeaderToken(
//        @RequestParam(name = "param1", required = false) String param1
    ) {
        ResponseDto<JwtTokens> responseDto = new ResponseDto<>();
        responseDto.setRtnCd(HttpStatus.OK.value());
        responseDto.setRtnMsg("정상 처리 완료 ");

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }

    /**
     * 동적인 쿼리 파라미터
     * */
    @PostMapping("/public/product/query")
    public ResponseEntity<?> queryWithoutHeaderToken(
        @RequestBody
    ) {
        ResponseDto<JwtTokens> responseDto = new ResponseDto<>();
        responseDto.setRtnCd(HttpStatus.OK.value());
        responseDto.setRtnMsg("정상 처리 완료 ");

        return ResponseEntity.status(responseDto.getRtnCd()).body(responseDto);
    }
}
