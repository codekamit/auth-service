package com.ecommerce.security.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(
            String message) {
        super(message);
    }
}
