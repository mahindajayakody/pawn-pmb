package com.m4.pawning.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;
import com.m4.core.bean.Trace;

@Entity
@Table(name="tblparametervalue")
public class ParameterValue extends Consolidate implements Auditable{
	private int parameterValueId;
	private int productId;
	private int parameterId;	
	private String paraValue;
	private Date effDate;
	
	@Transient
	public int getRecordId() {
		return parameterValueId;
	}

	public void setRecordId(int recordId) {
		this.parameterValueId=recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="ParameterValue")
	@Column(name="PARAMETERVALUEID")
	public int getParameterValueId() {
		return parameterValueId;
	}
	public void setParameterValueId(int parameterValueId) {
		this.parameterValueId = parameterValueId;
	}
	
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Column(name="PARAVALUE")
	public String getParaValue() {
		return paraValue;
	}
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	@Column(name="EFFDATE")
	public Date getEffDate() {
		return effDate;
	}
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}
	@Column(name="PARAMETERID")
	public int getParameterId() {
		return parameterId;
	}
	public void setParameterId(int parameterId) {
		this.parameterId = parameterId;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("PARAMETERVALUEID="+parameterValueId);
		stringBuilder.append(",PRODUCTID="+productId);
		stringBuilder.append(",PARAVALUE="+paraValue);
		stringBuilder.append(",EFFDATE="+effDate);
		stringBuilder.append(",PARAMETERID="+parameterId);
		
		return stringBuilder.toString();
	}
}
