package com.m4.core.exception;

public class CommonDataAccessException extends CommonRuntimeException {
      
    public CommonDataAccessException(Throwable cause, String exceptionCode){
        super(cause,exceptionCode);
    }    
    public CommonDataAccessException(String exceptionCode){
        super(exceptionCode);
    }
}
