package com.jms.ourhomeassignment.dto;

import lombok.Data;

@Data
public class RequestDto <T>{
    private T body;
    private String token;
}
