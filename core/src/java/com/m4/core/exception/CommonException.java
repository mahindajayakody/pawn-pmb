package com.m4.core.exception;

public class CommonException extends Exception{
    private String exceptionCode;    
    
    public CommonException(Throwable cause,String exceptionCode){
        super(cause);
        setExceptionCode(exceptionCode);
    }
    
    public CommonException(String exceptionCode){
        setExceptionCode(exceptionCode);
    }    

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
  
}
