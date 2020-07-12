package com.m4.core.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
	 public synchronized static boolean isValidSession(HttpServletRequest request){
		if ( request.getSession(false) == null || (request.getSession().getAttribute( "LOGIN_KEY" ) == null)){
		    return false;
		} else{
		    return true;
		}        
     }
	 
	 public synchronized static UserConfig getUserSession(HttpServletRequest request){
    	UserConfig userConfig = null;        
    	userConfig = (UserConfig) request.getSession().getAttribute("LOGIN_KEY");
        return userConfig;
    }
}
