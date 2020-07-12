package com.m4.core.util;



public enum RecordStatusEnum {
	PENDING(0),ACTIVE(1),CREATEPENDING(2),UPDATEPENDING(3),DELETEPENDING(4);
	
	private int code;
	private RecordStatusEnum(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static RecordStatusEnum getEnumByCode(int code) {
		Class clazz   = RecordStatusEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((RecordStatusEnum)o).getCode()==code)
				return (RecordStatusEnum)o;
		}
		return null;
	}
}
