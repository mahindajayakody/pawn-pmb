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

@Entity
@Table(name="tblduetype")
public class DueType extends Consolidate implements Auditable{
	private int dueTypeId;
	private String dueType;
	private String description;
	private int productId;
	private int sequence;
	private String isInternal;
	private String isReceipt;
	private String isODChargable;
	private String accountNumber; 
	

	@Transient
	public int getRecordId() {		
		return dueTypeId;
	}
	public void setRecordId(int recordId) {		
		this.dueTypeId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="DueType")
	@Column(name="DUETYPEID")
	public int getDueTypeId() {
		return dueTypeId;
	}
	public void setDueTypeId(int dueTypeId) {
		this.dueTypeId = dueTypeId;
	}	
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="CODE")	
	public String getDueType() {
		return dueType;
	}
	public void setDueType(String dueType) {
		this.dueType = dueType;
	}
	
	@Column(name="ISINTERNAL")	
	public String getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}
	
	@Column(name="ISODCHARGABLE")	
	public String getIsODChargable() {
		return isODChargable;
	}
	public void setIsODChargable(String isODChargable) {
		this.isODChargable = isODChargable;
	}
	
	@Column(name="ISRECEIPT")	
	public String getIsReceipt() {
		return isReceipt;
	}
	public void setIsReceipt(String isReceipt) {
		this.isReceipt = isReceipt;
	}
	
	@Column(name="PRDID")	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	@Column(name="SEQUENCE")	
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	@Column(name="ACCOUNTNO")
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("DUETYPEID="+dueTypeId);
		stringBuilder.append(",DESCRIPTION="+description);
		stringBuilder.append(",CODE="+dueType);
		stringBuilder.append(",ISINTERNAL="+isInternal);
		stringBuilder.append(",ISODCHARGABLE="+isODChargable);
		stringBuilder.append(",ISRECEIPT="+isReceipt);
		stringBuilder.append(",PRDID="+productId);
		stringBuilder.append(",SEQUENCE="+sequence);
		stringBuilder.append(",ACCOUNTNO="+accountNumber);
		
		return stringBuilder.toString();
	}
}
