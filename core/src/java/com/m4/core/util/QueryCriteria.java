package com.m4.core.util;

import java.io.Serializable;

public class QueryCriteria implements Serializable {
	private String fieldName;
	private Object fieldValue;
	private Oparator oparator;
	private boolean isAssociation;  
	
	public enum Oparator{
		EQUAL,NOT_EQUAL,GRATERTHAN,LESSTHAN,GRATERTHAN_OR_EQUAL,LESSTHAN_OR_EQUAL,LIKE
	};
		
	public QueryCriteria(){
		
	}
	public QueryCriteria(String fieldName,Oparator oparator,Object fieldValue){
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.oparator = oparator;
		this.isAssociation = false;
	}
	
	public QueryCriteria(String fieldName,Oparator oparator,Object fieldValue,boolean isAssociation){
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.oparator = oparator;
		this.isAssociation = isAssociation;
	}	
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Oparator getOparator() {
		return oparator;
	}
	public void setOparator(Oparator oparator) {
		this.oparator = oparator;
	}
	public boolean isAssociation() {
		return isAssociation;
	}
	public void setAssociation(boolean isAssociation) {
		this.isAssociation = isAssociation;
	}	
}
