package com.m4.pawning.util;

public enum UserStatusEnum {
	LOGEDIN(1),
	LOGEDOUT(0);
	
	private int code; 
	
	UserStatusEnum(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static UserStatusEnum getEnumByCode(int code) {
		Class clazz = UserStatusEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((UserStatusEnum)o).getCode()==code)
				return (UserStatusEnum)o;
		}
		return null;
	}

}
