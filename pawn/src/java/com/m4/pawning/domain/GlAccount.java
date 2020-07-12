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
import com.m4.core.bean.Consolidate;
import com.m4.core.bean.Trace;

@Entity
@Table(name="tblglaccount")
public class GlAccount extends Consolidate implements Auditable{	
	private int accountId;	
	private int productId;
	private String code;
	private String description;
	private String accountNo;
	private String drCr;
	

	@Transient
	public int getRecordId() {
		return accountId;
	}

	public void setRecordId(int recordId) {
		this.accountId=recordId;
	}	


	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="GlAccount")
	@Column(name="ACCOUNTID")
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
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
	
	@Column(name="ACCNO")
	public String getAccountCode() {
		return accountNo;
	}
	public void setAccountCode(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Column(name="DRCR")
	public String getDrCr() {
		return drCr;
	}
	public void setDrCr(String drCr) {
		this.drCr = drCr;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder =new StringBuilder();
		stringBuilder.append(",ACCOUNTID=" +accountId);
		return super.toString();
	}
	
}
