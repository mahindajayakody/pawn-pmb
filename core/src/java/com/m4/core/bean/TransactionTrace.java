package com.m4.core.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class TransactionTrace extends Persistent{   
	private Date lastUpdateDate;	
	private int userId;
	private int recordId;
	
	@Transient
    public abstract int getRecordId();
	public abstract void setRecordId(int recordId);
	
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
}
