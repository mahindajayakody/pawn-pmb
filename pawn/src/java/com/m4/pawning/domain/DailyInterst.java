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

import com.m4.core.bean.Consolidate;

@Entity
@Table(name="tbldailyinterest")
public class DailyInterst extends Consolidate {
	private int interestId;
	private int ticketId;
	private double interestRate;
	private double interstAmount;	
	private Date date;

	@Transient
	public int getRecordId() {
		return interestId;
	}

	
	public void setRecordId(int recordId) {
		interestId=recordId;
	}	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="DailyInterest")
    @Column(name="INTRESTID")
    public int getInterestId() {
		return interestId;
	}
	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}

	@Column(name="TKTID")
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	@Column(name="INTRATE")
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	@Column(name="INTAMOUNT")
	public double getInterstAmount() {
		return interstAmount;
	}
	public void setInterstAmount(double interstAmount) {
		this.interstAmount = interstAmount;
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
