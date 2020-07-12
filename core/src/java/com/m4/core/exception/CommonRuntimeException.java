package com.m4.core.exception;

public class CommonRuntimeException extends RuntimeException {
    private String exceptionCode;    

    public CommonRuntimeException(String exceptionCode){
        setExceptionCode(exceptionCode);
    }
    
    public CommonRuntimeException(Throwable cause,String productCode,String exceptionCode){
        super(cause);
        setExceptionCode(exceptionCode);
    }
    
    public CommonRuntimeException(Throwable cause,String exceptionCode){
        super(cause);
        setExceptionCode(exceptionCode);
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }   
}
