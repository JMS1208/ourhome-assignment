package com.jms.ourhomeassignment.dto.sign;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDto {
    private String id;
    private String pw;
}
