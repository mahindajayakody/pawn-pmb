package com.m4.core.util;

public class PasswordValidator {
	private static String  upperCaseChars = "(.*[A-Z].*)";
	private static String lowerCaseChars = "(.*[a-z].*)";
	private static String numbers = "(.*[0-9].*)";
	private static String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
	
	public PasswordValidator()
    {
            super();
    }

	/*
     * Password should be less than 15 and more than 8 characters in length.
     * Password should contain at least one upper case and one lower case alphabet.    
     * Password should contain at least one number. 
     * Password should contain at least one special character.
     */

    public String passwordValidation(String userName, String password)
    {
        if (password.length() > 15 || password.length() < 8)
                return "errors.passlen"; //"Password should be less than 15 and more than 8 characters in length.");
        
        if (password.indexOf(userName) > -1)
        	return "errors.pssuser"; //"Password Should not be same as user name");
        
        if (!password.matches(upperCaseChars ))
        	return "errors.pssupper"; //"Password should contain atleast one upper case alphabet");
        
        if (!password.matches(lowerCaseChars ))
        	return "errors.passlower"; //"Password should contain atleast one lower case alphabet");
        
        if (!password.matches(numbers ))
        	return "errors.passnum"; //"Password should contain atleast one number.");
        
        if (!password.matches(specialChars ))
        	return "errors.passspci"; //"Password should contain atleast one special character");
        
        return null;
    }
}
