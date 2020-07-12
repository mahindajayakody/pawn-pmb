package com.m4.pawning.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;

@Entity
@Table(name="tblbranchfundrequest")
public class FundRequest extends Consolidate implements Auditable{

	private int requestId;
	private String requestNo;
	private Date requestDate;
	private Double requestAmount;
	private int approvedBy;
	private Date approvedDate;
	private Officer officer;
	private String mail;
	private int requestedBranchId;

	@Transient
	public int getRecordId() {
		return requestId;
	}
	@Override
	public void setRecordId(int recordId) {
		this.requestId=recordId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="tblbranchfundrequest")
    @Column(name="REQUESTID")
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	@Column(name="REQUESTNO")
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	@Column(name="REQUESTDATE")
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@Column(name="REQUESTAMT")
	public Double getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(Double requestAmount) {
		this.requestAmount = requestAmount;
	}

	@Column(name="APPROVEDBY")
	public int getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name="APPROVEDDATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

//	@OneToOne(fetch=FetchType.LAZY,optional=true)
//	@JoinColumn(name="SUBMITOFF")
//	public Officer getOfficer() {
//		return officer;
//	}
//	public void setOfficer(Officer officer) {
//		this.officer = officer;
//	}
	@Column(name="EMAIL")
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name="REQBRNID")
	public int getRequestedBranchId() {
		return requestedBranchId;
	}
	public void setRequestedBranchId(int requestedBranchId) {
		this.requestedBranchId = requestedBranchId;
	}
}
