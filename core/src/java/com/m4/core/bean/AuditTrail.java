package com.m4.core.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "tblaudittrial")
public class AuditTrail implements Serializable {

	private static final long serialVersionUID = 3596761419860739831L;

	@Column(name="AUDITTRAILID")
	private int auditTrailId;
	@Column(name="TRNNO")
	private String trnNo;
	@Column(name="TABLENAME")
	private String tableName;
	@Column(name="PRIMARYKEY")
	private String primarykey;
	@Column(name="ACTION")
	private String action;
	@Column(name="BEFORESAVEDATA")
	private String beforeSaveData;
	@Column(name="AFTERSAVEDATA")
	private String afterSaveData;
	@Column(name="TRANDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date trandate;
	
	private transient Date fromDate;
	
	private transient Date toDate;
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "InvTab")
	@TableGenerator(name = "InvTab", table = "ID_GEN", pkColumnName = "ID_NAME", valueColumnName = "ID_VALUE", allocationSize = 1, pkColumnValue = "AuditTrail")
	public int getAuditTrailId() {
		return auditTrailId;
	}

	public void setAuditTrailId(int auditTrailId) {
		this.auditTrailId = auditTrailId;
	}
	
	public final String getBeforeSaveData() {
		return beforeSaveData;
	}

	public final void setBeforeSaveData(String beforeSaveData) {
		this.beforeSaveData = beforeSaveData;
	}

	public final String getAfterSaveData() {
		return afterSaveData;
	}

	public final void setAfterSaveData(String afterSaveData) {
		this.afterSaveData = afterSaveData;
	}

	public Date getTrandate() {
		return trandate;
	}

	public void setTrandate(Date trandate) {
		this.trandate = trandate;
	}

	public final String getTrnNo() {
		return trnNo;
	}

	public final void setTrnNo(String trnNo) {
		this.trnNo = trnNo;
	}

	public final String getTableName() {
		return tableName;
	}

	public final void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public final String getPrimarykey() {
		return primarykey;
	}

	public final void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}

	public final String getAction() {
		return action;
	}

	public final void setAction(String action) {
		this.action = action;
	}
	
	@Transient
	public final Date getFromDate() {
		return fromDate;
	}

	public final void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@Transient
	public final Date getToDate() {
		return toDate;
	}

	public final void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
