package com.bortolanza.fleet.common.exceptions;

public class BusinessException extends RuntimeException{
    public BusinessException(String mensage){
        super(mensage);
    }
    public BusinessException(String mensage, Throwable throwable){
        super(mensage,throwable);
    }
}
