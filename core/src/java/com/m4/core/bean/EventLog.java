package com.m4.core.bean;

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
@Table(name="tbleventlog")
public class EventLog extends Consolidate {
	private int productId;
	private String transactionId;
	private String programeName;	
	private int eventLogId;
	private int eventId;

	@Transient
	public int getRecordId() {
		return eventLogId;
	}
	
	public void setRecordId(int recordId) {
		eventLogId = recordId;

	}
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="EventLog")    		

    @Column(name="EVENTLOGID")		
	public int getEventLogId() {
		return eventLogId;
	}

	public void setEventLogId(int eventLogId) {
		this.eventLogId = eventLogId;
	}

	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name="TRANNO")
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Column(name="PRGNAME")
	public String getProgrameName() {
		return programeName;
	}

	public void setProgrameName(String programeName) {
		this.programeName = programeName;
	}

	@Column(name="EVENTID")
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
}
