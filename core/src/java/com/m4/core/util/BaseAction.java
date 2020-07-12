package com.m4.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.actions.DispatchAction;

public class BaseAction extends DispatchAction {
    public boolean isValidSession(HttpServletRequest request){
        if ( request.getSession(false) == null ||
            (request.getSession().getAttribute( "LOGIN_KEY" ) == null)){
            return false;
        } else{
            return true;
        }        
    }
    
    public UserConfig getUserSession(HttpServletRequest request){
    	UserConfig session = null;        
        session =( UserConfig) request.getSession().getAttribute("LOGIN_KEY");
        System.out.println("<HTTP Session ID> " + request.getSession().getId());
        return session;
    }
}
