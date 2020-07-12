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
@Table(name="tblreminderparameter")
public class ReminderPara extends Consolidate implements Auditable{
	
	private int reminderParaId;
	private int productId;
	private int schemeId;
	private String code;
	private String description;
	private int numberOfDays;
	private String isSendNominee;	
	
	@Transient
	public int getRecordId() {
		return reminderParaId;
	}

	@Override
	public void setRecordId(int recordId) {
		this.reminderParaId=recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="ReminderPara")
    		
  	@Column(name="REMPARAID")
	public int getReminderParaId() {
		return reminderParaId;
	}

	public void setReminderParaId(int reminderParaId) {
		this.reminderParaId = reminderParaId;
	}
  		

    @Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Column(name="SCHEMEID")
	public int getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
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
	
	@Column(name="NOOFDAYS")
	public int getNumberOfDays() {
		return numberOfDays;
	}
	
	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	@Column(name="ISSENDNOMINEE")
	public String getIsSendNominee() {
		return isSendNominee;
	}

	public void setIsSendNominee(String isSendNominee) {
		this.isSendNominee = isSendNominee;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("REMPARAID="+reminderParaId);
		stringBuilder.append(",PRODUCTID="+productId);
		stringBuilder.append(",SCHEMEID="+schemeId);
		stringBuilder.append(",CODE="+code);
		stringBuilder.append(",DESCRIPTION="+description);
		stringBuilder.append(",NOOFDAYS="+numberOfDays);
		stringBuilder.append(",ISSENDNOMINEE="+isSendNominee);
		return stringBuilder.toString();
	}
}
