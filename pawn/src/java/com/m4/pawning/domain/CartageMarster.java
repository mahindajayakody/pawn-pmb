package com.m4.pawning.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Trace;


@Entity      
@Table(name="tblcartagemarster")
public class CartageMarster extends Trace implements Auditable{
	private int cartageMarsterId;
	private String code;
	private String description;
	private int companyId;

	@Transient
	public int getRecordId() {		
		return cartageMarsterId;
	}
	public void setRecordId(int recordId) {
		this.cartageMarsterId = recordId;
	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="CartageMarster")
	@Column(name="MCARTAGEID")
	public int getCartageMarsterId() {
		return cartageMarsterId;
	}
	public void setCartageMarsterId(int cartageMarsterId) {
		this.cartageMarsterId = cartageMarsterId;
	}
	
	@Column(name="CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder =new StringBuilder();
		stringBuilder.append("MCARTAGEID="+cartageMarsterId);
		stringBuilder.append(",CODE="+code);
		stringBuilder.append(",DESCRIPTION="+description);
		stringBuilder.append(",COMID="+companyId);
		return stringBuilder.toString();
	}
}
