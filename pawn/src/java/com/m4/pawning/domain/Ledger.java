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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Consolidate;

@Entity
@Table(name="tblledger")
public class Ledger extends Consolidate {
	
	private int ledgerId;
	private int productId;
	private DueType dueType;
	private double creditAmount;
	private double debitAmount;
	private int ticketId;
	private Date date;
	
	

	@Transient
	public int getRecordId() {
		return ledgerId;
	}
	public void setRecordId(int recordId) {
		ledgerId=recordId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Ledger")
	@Column(name="LEDGERID")
	public int getLedgerId() {
		return ledgerId;
	}

	public void setLedgerId(int ledgerId) {
		this.ledgerId = ledgerId;
	}

	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name="CRAMOUNT")
	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	@Column(name="DRAMOUNT")
	public double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="DUETYPEID")
	public DueType getDueType() {
		return dueType;
	}
	public void setDueType(DueType dueType) {
		this.dueType = dueType;
	}
	/**
	 * @return the ticketId
	 */
	@Column(name="TKTID")
	public int getTicketId() {
		return ticketId;
	}
	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	/**
	 * @return the date
	 */
	@Column(name="DATE")
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
