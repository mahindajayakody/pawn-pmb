package com.m4.pawning.util;

public enum TicketStatusEnum {
	ACTIVE(1),
	PENDING(2),	
	REJECTED(3),
	LAPS(4),
	CLOSSED(5);
	
	private int code; 
	
	TicketStatusEnum(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static TicketStatusEnum getEnumByCode(int code) {
		Class clazz = TicketStatusEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((TicketStatusEnum)o).getCode()==code)
				return (TicketStatusEnum)o;
		}
		return null;
	}
}
