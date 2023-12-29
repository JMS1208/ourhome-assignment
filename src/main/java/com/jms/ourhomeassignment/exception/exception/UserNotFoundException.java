package com.jms.ourhomeassignment.exception.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
       super(message);
    }
}
