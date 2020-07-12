package com.m4.core.exception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class AuthorizationInterceptor implements MethodInterceptor {

    private static Logger logger = Logger.getLogger(AuthorizationInterceptor.class);    
    
    public Object invoke(MethodInvocation method) throws Throwable {
        double start = System.currentTimeMillis( );
        logger.info("<Entered to Authentication Interceptor>");
        logger.info(method.getMethod().getName());
        Object obj = method.proceed();
      
        return obj;
    }
}
