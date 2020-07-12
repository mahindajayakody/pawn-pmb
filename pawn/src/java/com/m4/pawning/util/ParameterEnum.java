package com.m4.pawning.util;

public enum ParameterEnum {
	MININTERESTDAYS(1),
	MAXPAWNADDFORCUSTOMER(2),
	MAXPAWNADDFORNONCUSTOMER(3),
	MININTEREST(4),
	DOCUMENTCHARGES(28),	
	DOCUMENTCHARGES1(31),
	
	MININTERESTDAYS1(5),
	MAXPAWNADDFORCUSTOMER1(6),
	MAXPAWNADDFORNONCUSTOMER1(7),
	MININTEREST1(8),
	PASSWORDEXPIREPERIOD(41),
	PASSWORDREPEATTIME(42);

	private int code;

	ParameterEnum(int code){
		this.code = code;
	}
	public int getCode() {
		return code;
	}

	public static ParameterEnum getEnumByCode(int code) {
		Class clazz = ParameterEnum.class;
		Object[] cons = clazz.getEnumConstants();
		for(Object o:cons) {
			if(((ParameterEnum)o).getCode()==code)
				return (ParameterEnum)o;
		}
		return null;
	}

}
