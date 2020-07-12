package com.m4.pawning.domain;

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
@Table(name="tblduefrom")
public class DueFrom extends TransactionConsolidate {
	private int dueFromId;
	private int productId;
	private int ticketId;
	private int dueTypeId;
	private double dueAmount;
	private double paidAmount;
	private double balanceAmount;
	private String dueTypeDescriiption;

	
	@Transient
	public int getRecordId() {
		return dueFromId;
	}
	public void setRecordId(int recordId) {
		this.dueFromId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="DueFrom")
	@Column(name="DUEFROMID")
	public int getDueFromId() {
		return dueFromId;
	}
	public void setDueFromId(int dueFromId) {
		this.dueFromId = dueFromId;
	}
	
	@Column(name="BALAMOUNT")
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	@Column(name="DUEAMOUNT")
	public double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	@Column(name="DUETYPEID")
	public int getDueTypeId() {
		return dueTypeId;
	}
	public void setDueTypeId(int dueTypeId) {
		this.dueTypeId = dueTypeId;
	}
	
	@Column(name="PAIDAMOUNT")
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
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
	
	@Transient
	public String getDueTypeDescriiption() {
		return dueTypeDescriiption;
	}
	public void setDueTypeDescriiption(String dueTypeDescriiption) {
		this.dueTypeDescriiption = dueTypeDescriiption;
	}
}
