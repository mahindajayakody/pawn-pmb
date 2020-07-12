package com.m4.core.util;

import java.util.ArrayList;
import java.util.List;


public class ValidateUtil {
	
	public static List validateFormAll(String validateForm) {
		List errorList = new ArrayList();
		try {
			int propertyInit = 0;
			int propertyEnd = 0;
			int resEnd = 0;
			int screenLabelEnd = 0;
			String mes = new String(validateForm);
			mes=mes.trim();
			mes=mes.replace("]]}", "]],");
			String[] arr = mes.split("]],");
			for (int m = 0; m < arr.length; m++) {
				arr[m]=arr[m].trim();
				char mesChar[] = arr[m].toCharArray();
				for (int i = 0; i < mesChar.length; i++) {
					if (mesChar[0] == '{')
						propertyInit = 1;
					else
						propertyInit = 0;
					if (mesChar[i] == '=')
						propertyEnd = i;
					if (mesChar[i] == '[')
						resEnd = i;
					if( mesChar[i]==',' ) {
                        screenLabelEnd = i;
						break;
					}else {
                        screenLabelEnd = arr[m].length();
					}
				}
				try {
					errorList.add((arr[m].toString()).substring(propertyInit, propertyEnd));
					errorList.add((arr[m].toString()).substring(propertyEnd + 2, resEnd));
					errorList.add((arr[m].toString()).substring(resEnd + 1,screenLabelEnd ));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return errorList;
	}
	public static List validateFormOne(String validateForm,String searchField) {
		List errorList = new ArrayList();
		try{
			int resEnd = 0;
			int screenLabelEnd = 0;
			char mesChar[] = validateForm.toCharArray();
	        for(int i = 0; i < mesChar.length; i++){
	            if(mesChar[i]=='[')
	                resEnd = i;
	            else if( mesChar[i]==',' ){
	                screenLabelEnd = i;
	                break;
	            }
	            else if ( mesChar[i]==']' )
	                 screenLabelEnd = i;
	        }
	        errorList.add(searchField);
	        errorList.add( validateForm.substring(0,resEnd)+"");
	        errorList.add( validateForm.substring(resEnd+1,screenLabelEnd));	
	    }catch(Exception e){
			System.out.println(e);
		}
		return errorList;		
	}
}
