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

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;

@Entity
@Table(name="tblbranchfunddeposit")
public class FundDeposit extends Consolidate implements Auditable{

	private int depositId;
	private String depositNo;
	private Date depositDate;
	private Double depositAmount;
	private int approvedBy;
	private Date approvedDate;
	private int ticketCount;
	private int receiptCount;
	private Double tiketedAmount;
	private Double receiptedAmount;
	private String mail;
	private int depositeBranchId;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="tblbranchfunddeposit")
    @Column(name="DEPOSITID")
	public int getDepositId() {
		return depositId;
	}

	public void setDepositId(int depositId) {
		this.depositId = depositId;
	}
	@Column(name="DEPOSITNO")
	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}
	@Column(name="DEPOSITDATE")
	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}
	@Column(name="DEPOSITAMT")
	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
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

	@Transient
	public int getRecordId() {
		return depositId;
	}

	@Override
	public void setRecordId(int recordId) {
		this.depositId=recordId;
	}

	@Column(name="TICKETCOUNT")
	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}

	@Column(name="RECEIPTCOUNT")
	public int getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(int receiptCount) {
		this.receiptCount = receiptCount;
	}

	@Column(name="TICKETEDAMT")
	public double getTicketedAmount() {
		return tiketedAmount;
	}
	public void setTicketedAmount(double tiketedAmount) {
		this.tiketedAmount = tiketedAmount;
	}

	@Column(name="RECEIPTEDAMT")
	public double getReceiptedAmount() {
		return receiptedAmount;
	}
	public void setReceiptedAmount(double receiptedAmount) {
		this.receiptedAmount = receiptedAmount;
	}

	@Column(name="EMAIL")
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name="DEPOBRNID")
	public int getDepositeBranchId() {
		return depositeBranchId;
	}
	public void setDepositeBranchId(int depositeBranchId) {
		this.depositeBranchId = depositeBranchId;
	}
}
