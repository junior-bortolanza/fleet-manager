package com.bortolanza.fleet.modules.exceptions;


public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
