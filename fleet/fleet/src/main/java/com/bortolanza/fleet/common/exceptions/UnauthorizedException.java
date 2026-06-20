package com.bortolanza.fleet.common.exceptions;


public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
