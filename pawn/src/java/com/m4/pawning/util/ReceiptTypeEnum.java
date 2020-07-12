package com.m4.pawning.util;

public enum ReceiptTypeEnum {
	CASH(1),
	CHEQUE(2);	
	
	private int code; 
	
	ReceiptTypeEnum(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static ReceiptTypeEnum getEnumByCode(int code) {
		Class clazz = ReceiptTypeEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((ReceiptTypeEnum)o).getCode()==code)
				return (ReceiptTypeEnum)o;
		}
		return null;
	}
}
