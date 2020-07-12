package com.m4.pawning.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.m4.core.bean.Persistent;

@Entity
@Table(name="tblsystemdate")
public class SystemDate extends Persistent{
	private int systemDateId;
	private int companyId;
	private int branchId;
	private Date previousDate;
	private Date currentDate;
	private Date nextDate;
	private int lastUpdateUserId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="SystemDate")
	@Column(name="SYSDATEID")
	public int getSystemDateId() {
		return systemDateId;
	}
	public void setSystemDateId(int systemDateId) {
		this.systemDateId = systemDateId;
	}
	
	@Column(name="BRANCHID")
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="CURDATE")
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
	@Column(name="LASTUPUSERID")
	public int getLastUpdateUserId() {
		return lastUpdateUserId;
	}
	public void setLastUpdateUserId(int lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
	
	@Column(name="NXTDATE")
	public Date getNextDate() {
		return nextDate;
	}
	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}
	
	@Column(name="PRVDATE")
	public Date getPreviousDate() {
		return previousDate;
	}
	public void setPreviousDate(Date previousDate) {
		this.previousDate = previousDate;
	}
}
