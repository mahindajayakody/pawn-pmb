package com.m4.core.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


@MappedSuperclass
public abstract class Trace extends Persistent{	
	
	private int recordId;		
    private int originalRecordId;	
	private Date lastUpdateDate;	
	private int userId;
	private int recordStatus;	
	private String transactionId;
	    
	@Transient
    public abstract int getRecordId();
	public abstract void setRecordId(int recordId);
	
	@Column(name="ORIGINALID")
	public int getOriginalRecordId() {
		return originalRecordId;
	}
	public void setOriginalRecordId(int originalRecordId) {
		this.originalRecordId = originalRecordId;
	}
	
	@Column(name="LASTUPUSERID")
	public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    @Column(name="LASTUPDATE")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	@Column(name="RECSTATUS")
	public int getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}
	@Transient
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
