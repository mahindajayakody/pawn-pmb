package com.m4.pawning.util;


public enum ReminderCodeEnum {
	REM1("REM1"),
	REM2("REM2"),
	REM3("REM3");
	
	private String code; 
	
	ReminderCodeEnum(String code){
		this.code = code;
	}	
	public String getCode() {
		return code;
	}
	
	public static ReminderCodeEnum getEnumByCode(String code) {
		Class clazz = ReminderCodeEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((ReminderCodeEnum)o).getCode().equals(code))
				return (ReminderCodeEnum)o;
		}
		return null;
	}
}
