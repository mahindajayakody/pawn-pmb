package com.m4.core.exception;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleObjectStateException;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;


public class ExceptionInterceptor implements ThrowsAdvice{

    private static Logger logger = Logger.getLogger(ExceptionInterceptor.class);

    public void afterThrowing(Exception ex) throws Throwable {
        Throwable t = ex.getCause();
        logger.info("***Entered to Exception Interceptor***");
        while(t != null){
            if (t instanceof SQLException){
                //SQLException sqlex = (SQLException)ex.getCause();
                SQLException sqlex = (SQLException)t;
                logger.error("SQL Error code :" + sqlex.getErrorCode(), ex);
                throw new PawnException(sqlex.getMessage());
            }else{
                t=t.getCause();
            }
        }
        //added this to check if stale data exception is caught.
        if(ex instanceof StaleObjectStateException){
            logger.error("StaleObjectStateException",ex);
            throw new PawnException("hibernate.staledata");
        }

        //added this to check if stale data exception is caught.
        if (ex instanceof HibernateOptimisticLockingFailureException){
            logger.error("HibernateOptimisticLockingFailureException",ex);
            throw new PawnException("hibernate.staledata");
        }
        if(ex instanceof ObjectRetrievalFailureException){
            logger.error("ObjectRetrievalFailureException",ex);
            throw new PawnException("hibernate.objectnotfound");
        }
        if(ex instanceof DataIntegrityViolationException){
            logger.error("DataIntegrityViolationException",ex);
            throw new PawnException("hibernate.integrity");
        }
        if(ex instanceof ObjectNotFoundException){
            logger.error("ObjectNotFoundException",ex);
            throw new PawnException("hibernate.objectnotfound");
        }
        if (ex instanceof CommonDataAccessException){ //Manually thrown exception from DAO/BL
            CommonDataAccessException cde = (CommonDataAccessException)ex;
            logger.error("CommonDataAccessException code :- " + ((CommonDataAccessException)ex).getExceptionCode() ,ex);
            if(cde.getExceptionCode()==null){
                cde.setExceptionCode("unidentified.error");
                logger.error("undefine " + cde);
                throw new PawnException(cde.getExceptionCode());
            }else{
            	logger.error("user " + cde);
                throw new PawnException(cde.getExceptionCode());
            }
        }
        if (ex instanceof SQLException){
            logger.error("SQL Error code :" + ((SQLException)ex).getErrorCode(), ex);
            throw new PawnException(((SQLException)ex).getMessage());
        }
   }
}
