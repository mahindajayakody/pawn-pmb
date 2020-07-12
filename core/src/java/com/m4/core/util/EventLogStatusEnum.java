package com.m4.core.util;

public enum EventLogStatusEnum {
	CREATE(1),
	UPDATE(2),
	DELETE(3),
	AUTHORIZED(4),
	APPROVE(5),
	VIEW(6),
	REJECT(7);
	
	private int code; 
	
	EventLogStatusEnum(int code){
		this.code = code;
	}	
	public int getCode() {
		return code;
	}
	
	public static EventLogStatusEnum getEnumByCode(int code) {
		Class clazz = EventLogStatusEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((EventLogStatusEnum)o).getCode()==code)
				return (EventLogStatusEnum)o;
		}
		return null;
	}
}
