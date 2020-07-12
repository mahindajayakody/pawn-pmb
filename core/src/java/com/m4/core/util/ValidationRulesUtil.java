package com.m4.core.util;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;
import org.apache.struts.validator.validwhen.ValidWhen;


public class ValidationRulesUtil {
	// Check Zero value of the field (data type is Double)
	public static boolean validateNonZeroDouble(Object bean,
			ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		Double code = Double.parseDouble(ValidatorUtils.getValueAsString(bean,
				field.getProperty()));

		if (code == 0) {
			errors.add(field.getKey(), Resources.getActionMessage(validator,
					request, va, field));
			return false;
		} else {
			return true;
		}
	}
//		 Check Zero value of the field (data type is Integer)
	public static boolean validateNonZeroInt(Object bean,
			ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		int code = Integer.parseInt(ValidatorUtils.getValueAsString(bean,field.getProperty()));

		if (code == 0) {
			errors.add(field.getKey(), Resources.getActionMessage(validator,
					request, va, field));
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validateNIC(Object bean,
			ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		String strNIC = ValidatorUtils.getValueAsString(bean,field.getProperty());
		
        int[] a ={ 3, 2, 7, 6, 5, 4, 3, 2 };
        int[] b ={ 0, 0, 0, 0, 0, 0, 0, 0 };
        int totNic = 0;

        if (strNIC.length() == 10) {
            if (strNIC.endsWith("V") || strNIC.endsWith("X")) {
                for (int i = 0; i < strNIC.length() - 2; i++) {
                    b[i] = Integer.parseInt(""+strNIC.charAt(i));
                    totNic = totNic + Integer.parseInt(""+strNIC.charAt(i)) * a[i];
                }
                if (((totNic % 11) <= 1 && Integer.parseInt(""+strNIC.charAt(8)) == 0 || Integer.parseInt(""+strNIC.charAt(8)) == 11 - (totNic % 11))){
                    return true;
                }
            }
        }
        errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
        return false;
    }
	
	@SuppressWarnings("static-access")
	public static boolean validateValidWhenNIC(Object bean,
			ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		String strNIC = ValidatorUtils.getValueAsString(bean,field.getProperty());
		
		if (strNIC==null || strNIC.equals("")) {
			String temp = va.getMsg();
			va.setMsg("errors.required");
			errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			va.setMsg(temp);
			return false;
		}
		
		boolean result =  ValidWhen.validateValidWhen(bean, va, field, errors, validator, request);
		if(!result) return false;
		else if(request.getParameter("comOrIndiv").equals("I")){
	        int[] a ={ 3, 2, 7, 6, 5, 4, 3, 2 };
	        int[] b ={ 0, 0, 0, 0, 0, 0, 0, 0 };
	        int totNic = 0;
	        int year = 0;
	        
	
	        if (strNIC.length() == 10) {
	        	
//	            if (strNIC.endsWith("V") || strNIC.endsWith("X")) {
//	                for (int i = 0; i < strNIC.length() - 2; i++) {
//	                    b[i] = Integer.parseInt(""+strNIC.charAt(i));
//	                    totNic = totNic + Integer.parseInt(""+strNIC.charAt(i)) * a[i];
//	                }
//	                if (((totNic % 11) <= 1 && Integer.parseInt(""+strNIC.charAt(8)) == 0 || Integer.parseInt(""+strNIC.charAt(8)) == 11 - (totNic % 11))){
//	                    return true;
//	                }
//	            }
	        }else{
	        	year = Integer.parseInt(strNIC.substring(0,4)); 
	        	if ((Calendar.getInstance().YEAR - year) >= 18) {
	        		return true;
	        	}
	        }
	        errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
		}    
        return false;
    }
	@SuppressWarnings("static-access")
	public String validateAge(String nic){
		int year = 0;
		String nicYear = "";
		 
		
		if (nic.length() == 10){
			nicYear = nic.substring(0,2);
			if (Integer.parseInt(nicYear) >= 40 && Integer.parseInt(nicYear) <= 99){
				return "";
			}
		}else if (nic.length() > 10){
			year = Integer.parseInt(nic.substring(0,4));
			if ((Calendar.getInstance().YEAR - year) >= 18){
				return "";
			} 
		}else{
			return "error.invalidnic";
		}
		return "error.age";
	}

}



