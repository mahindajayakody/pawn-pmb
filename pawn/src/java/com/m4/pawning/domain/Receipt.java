package com.m4.pawning.domain;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.TransactionConsolidate;

@Entity
@Table(name="tblreceipt")
public class Receipt extends TransactionConsolidate implements Auditable{
	private int receiptId;
	private int productId;
	private int ticketId;
	private int pawnerId;
	private String receiptNo;
	private String description;
	private double receiptAmt;
	private Date receiptDate;
	private int receiptType;
	private int prtNo;
	private Date printDate;
	private String chequeNo;
	private Date chequeDate;
	private int status;
	private String receiptEnteredUser;
	private Collection<DueReceipt> dueReceipts;
	private Collection<OverPayment> overPayment;
	
	
	@Transient
	public int getRecordId() {		
		return receiptId;
	}
	public void setRecordId(int recordId) {
		this.receiptId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Receipt")
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
	
	@Column(name="TICKETID")
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	@Column(name="RCPNO")
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	@Column(name="PAWNERID")
	public int getPawnerId() {
		return pawnerId;
	}
	public void setPawnerId(int pawnerId) {
		this.pawnerId = pawnerId;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="RCPAMOUNT")
	public double getReceiptAmt() {
		return receiptAmt;
	}
	public void setReceiptAmt(double receiptAmt) {
		this.receiptAmt = receiptAmt;
	}
	
	@Column(name="RCPDATE")
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	@Column(name="RCPTYPE")
	public int getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(int receiptType) {
		this.receiptType = receiptType;
	}
	@Column(name="PRINTNO")
	public int getPrtNo() {
		return prtNo;
	}
	public void setPrtNo(int prtNo) {
		this.prtNo = prtNo;
	}
	@Column(name="PRINTDATE")
	public Date getPrintDate() {
		return printDate;
	}
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}
	@Column(name="CHEQUENO")
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	@Column(name="CHEQUEDATE")
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	
	@Column(name="STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name="RPCENTUSER")
	public String getReceiptEnteredUser() {
		return receiptEnteredUser;
	}
	public void setReceiptEnteredUser(String receiptEnteredUser) {
		this.receiptEnteredUser = receiptEnteredUser;
	}
	
	@OneToMany(cascade = {CascadeType.PERSIST},fetch=FetchType.LAZY,mappedBy="receiptId")
	public Collection<DueReceipt> getDueReceipts() {
		return dueReceipts;
	}
	public void setDueReceipts(Collection<DueReceipt> dueReceipts) {
		this.dueReceipts = dueReceipts;
	}
	
//	@OneToMany(cascade = {CascadeType.PERSIST},fetch=FetchType.LAZY,mappedBy="receiptNo")
//	public Collection<OverPayment> getOverpayments() {
//		return overPayment;
//	}
//	public void setOverpayments(Collection<OverPayment> overPayment) {
//		this.overPayment = overPayment;
//	}	
}
