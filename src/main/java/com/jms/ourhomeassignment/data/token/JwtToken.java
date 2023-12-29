package com.jms.ourhomeassignment.data.token;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class JwtToken {
    public String value;
    public Date expiredAt;
}
