package com.m4.pawning.domain;

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
@Table(name="tblauctioexpences")
public class AuctionExpences extends Consolidate implements Auditable{
	private int auctionExcpenceId;
	private int productId;
	private int auctionId;
	private int duetypeId;
	private double amount;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="AuctionExpences")
    @Column(name="AUCTIONDETID")		
	public int getAuctionExcpenceId() {
		return auctionExcpenceId;
	}

	public void setAuctionExcpenceId(int auctionExcpenceId) {
		this.auctionExcpenceId = auctionExcpenceId;
	}

	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name="AUCTIONID")
	public int getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(int auctionId) {
		this.auctionId = auctionId;
	}

	@Column(name="DUETYPEID")
	public int getDuetypeId() {
		return duetypeId;
	}

	public void setDuetypeId(int duetypeId) {
		this.duetypeId = duetypeId;
	}

	@Column(name="AMOUNT")
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
    @Transient		
	public int getRecordId() {
		// TODO Auto-generated method stub
		return auctionExcpenceId;
	}

	public void setRecordId(int recordId) {
		auctionExcpenceId=recordId;

	}

	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("auctionExcpenceId =" + auctionExcpenceId);
		stringBuilder.append(",productId =" + productId);
		stringBuilder.append(",auctionId =" + auctionId);
		stringBuilder.append(",duetypeId =" + duetypeId);
		stringBuilder.append(",amount =" + amount);
		return stringBuilder.toString();		
	}
}
