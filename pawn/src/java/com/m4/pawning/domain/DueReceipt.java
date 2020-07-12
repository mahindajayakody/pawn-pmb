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

import com.m4.core.bean.TransactionConsolidate;

@Entity
@Table(name="tblduereceipt")
public class DueReceipt extends TransactionConsolidate {
	private int dueReceiptId;
	private int ticketId;
	private int dueTypeId;
	private double settleAmount;
	private Date settledDate;	
	private int refNumber;
	private int receiptId;
	private int productId;
	private int pownerId;
	
	@Transient
	public int getRecordId() {
		return dueReceiptId;
	}
	public void setRecordId(int recordId) {
		this.dueReceiptId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="DueReceipt")
	@Column(name="DUERCPID")
	public int getDueReceiptId() {
		return dueReceiptId;
	}
	public void setDueReceiptId(int dueReceiptId) {
		this.dueReceiptId = dueReceiptId;
	}
	
	@Column(name="DUETYPEID")
	public int getDueTypeId() {
		return dueTypeId;
	}
	public void setDueTypeId(int dueTypeId) {
		this.dueTypeId = dueTypeId;
	}
	
	@Column(name="SETAMOUNT")
	public double getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(double settleAmount) {
		this.settleAmount = settleAmount;
	}
	
	@Column(name="SETDATE")
	public Date getSettledDate() {
		return settledDate;
	}
	public void setSettledDate(Date settledDate) {
		this.settledDate = settledDate;
	}
	
	@Column(name="TICKETID")
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	@Column(name="REFNO")
	public int getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(int refNumber) {
		this.refNumber = refNumber;
	}

	@Column(name="RCPID")
	public int getReceiptId() {
		return receiptId;
	}
	
	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name="PAWNERID")
	public int getPownerId() {
		return pownerId;
	}
	public void setPownerId(int pownerId) {
		this.pownerId = pownerId;
	}
}
