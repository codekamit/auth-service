package com.ecommerce.security.exceptions;

import javax.naming.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    public CustomAuthenticationException(String msg) {
        super(msg);
    }
}

