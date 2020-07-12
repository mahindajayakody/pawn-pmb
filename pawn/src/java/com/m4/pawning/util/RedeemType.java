package com.m4.pawning.util;

public enum RedeemType {
	REDEEM(1),
	RENEW(2);
	
	private int code; 
	
	RedeemType(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static RedeemType getEnumByCode(int code) {
		Class clazz = RedeemType.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((RedeemType)o).getCode()==code)
				return (RedeemType)o;
		}
		return null;
	}
}
