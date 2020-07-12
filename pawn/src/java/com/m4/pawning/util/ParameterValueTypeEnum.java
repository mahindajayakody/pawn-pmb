package com.m4.pawning.util;

public enum ParameterValueTypeEnum {
	DECIMAL("Decimal"),
	STRING("String"),
	LOGICAL("Logical");
	
	private String code; 
	
	ParameterValueTypeEnum(String code){
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static ParameterValueTypeEnum getEnumByCode(String code) {
		Class clazz = ParameterValueTypeEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((ParameterValueTypeEnum)o).getCode().equals(code))
				return (ParameterValueTypeEnum)o;
		}
		return null;
	}
	
}
