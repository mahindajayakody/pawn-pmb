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
@Table(name="tblticketredeem")
public class TicketRedeem extends TransactionConsolidate {
	private int redeemId;
	private int ticketId;
	private int productId;
	private int newTicketId;
	private double totalPaidAmount;
	private int redeemType;
	private int redeemUserId;
	private Date redeemDate;
	private int approveUserId;
	private Date approveDate;
	
	@Transient
	public int getRecordId() {		
		return redeemId;
	}
	public void setRecordId(int recordId) {
		this.redeemId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="TicketRedeem")
	@Column(name="REDEMID")
	public int getRedeemId() {
		return redeemId;
	}
	public void setRedeemId(int redeemId) {
		this.redeemId = redeemId;
	}
	
	@Column(name="APPDATE")
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	@Column(name="APPUPUSERID")
	public int getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(int approveUserId) {
		this.approveUserId = approveUserId;
	}
	
	@Column(name="NEWTICKETID")
	public int getNewTicketId() {
		return newTicketId;
	}
	public void setNewTicketId(int newTicketId) {
		this.newTicketId = newTicketId;
	}
	
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	@Column(name="REDDATE")
	public Date getRedeemDate() {
		return redeemDate;
	}
	public void setRedeemDate(Date redeemDate) {
		this.redeemDate = redeemDate;
	}
	
	@Column(name="REDEMTYP")
	public int getRedeemType() {
		return redeemType;
	}
	public void setRedeemType(int redeemType) {
		this.redeemType = redeemType;
	}
	
	@Column(name="REDUPUSERID")
	public int getRedeemUserId() {
		return redeemUserId;
	}
	public void setRedeemUserId(int redeemUserId) {
		this.redeemUserId = redeemUserId;
	}
	
	@Column(name="TICKETID")
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	@Column(name="TOTPAID")
	public double getTotalPaidAmount() {
		return totalPaidAmount;
	}
	public void setTotalPaidAmount(double totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}
}
