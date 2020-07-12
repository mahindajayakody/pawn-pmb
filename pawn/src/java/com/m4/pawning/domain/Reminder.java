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

import com.m4.core.bean.Consolidate;

@Entity
@Table(name="tblreminders")
public class Reminder extends Consolidate {
	private int reminderId;
	private int reminderParaId;
	private int productId;
	private int schemeId;
	private int ticketId;
	private int isPrinted;
	private Date dateGenerated;
	private Date datePrinted;
	private Double capitalOutsanding;
	private Double interestOutstanding;
	private Double otherOutstanding;
	private String reminderParaCode;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Reminder")
    		
  	@Column(name="REMINDERID")
	public int getReminderId() {
		return reminderId;
	}

	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}
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
	@Column(name="TICKETID")
	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	@Column(name="ISPRINTED")
	public int getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(int isPrinted) {
		this.isPrinted = isPrinted;
	}
	@Column(name="DATEGENERATED")
	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}
	@Column(name="DATEPRINTED")
	public Date getDatePrinted() {
		return datePrinted;
	}

	public void setDatePrinted(Date datePrinted) {
		this.datePrinted = datePrinted;
	}
	@Column(name="CAPOUT")
	public Double getCapitalOutsanding() {
		return capitalOutsanding;
	}

	public void setCapitalOutsanding(Double capitalOutsanding) {
		this.capitalOutsanding = capitalOutsanding;
	}
	@Column(name="INTOUT")
	public Double getInterestOutstanding() {
		return interestOutstanding;
	}

	public void setInterestOutstanding(Double interestOutstanding) {
		this.interestOutstanding = interestOutstanding;
	}
	@Column(name="OTHOUT")
	public Double getOtherOutstanding() {
		return otherOutstanding;
	}

	public void setOtherOutstanding(Double otherOutstanding) {
		this.otherOutstanding = otherOutstanding;
	}

	@Transient
	public int getRecordId() {
		return reminderId;
	}

	@Override
	public void setRecordId(int recordId) {
		this.reminderId =recordId; 
	}
	@Column(name="REMPARACODE")
	public String getReminderParaCode() {
		return reminderParaCode;
	}

	public void setReminderParaCode(String reminderParaCode) {
		this.reminderParaCode = reminderParaCode;
	}
	
	

}
