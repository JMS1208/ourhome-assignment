package com.jms.ourhomeassignment.component.jwt;

import io.jsonwebtoken.Claims;

import java.util.List;

public interface JwtProvider {


    private Claims createClaims(String email, List<String> roles);
}
