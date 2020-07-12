package com.m4.pawning.util;

public enum ReceiptStatusEnum {
	ACTIVE(1),
	CANCEL(2);
	
	private int code; 
	
	ReceiptStatusEnum(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static ReceiptStatusEnum getEnumByCode(int code) {
		Class clazz = ReceiptStatusEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((TicketStatusEnum)o).getCode()==code)
				return (ReceiptStatusEnum)o;
		}
		return null;
	}
}
