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
@Table(name="tblinterestslabs")
public class InterestSlab extends Consolidate implements Auditable{
	private int interestSlabId;
	private int productId;
	private int interestSlabCode;	
	private int slabNo;
	private int noOfDaysFrom;
	private int noOfDaysTo;
	private double rate;
	
	@Transient
	public int getRecordId() {
		return interestSlabId;
	}
	public void setRecordId(int recordId) {
		this.interestSlabId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="InterestSlab")
	@Column(name="INTSLABID")
	public int getInterestSlabId() {
		return interestSlabId;
	}
	public void setInterestSlabId(int interestSlabId) {
		this.interestSlabId = interestSlabId;
	}
	
	@Column(name="CODE")
	public int getInterestSlabCode() {
		return interestSlabCode;
	}
	public void setInterestSlabCode(int interestSlabCode) {
		this.interestSlabCode = interestSlabCode;
	}
	
	@Column(name="NODAYSFROM")
	public int getNoOfDaysFrom() {
		return noOfDaysFrom;
	}
	public void setNoOfDaysFrom(int noOfDaysFrom) {
		this.noOfDaysFrom = noOfDaysFrom;
	}
	
	@Column(name="NODAYSTO")
	public int getNoOfDaysTo() {
		return noOfDaysTo;
	}
	public void setNoOfDaysTo(int noOfDaysTo) {
		this.noOfDaysTo = noOfDaysTo;
	}
	
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	@Column(name="RATE")
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	@Column(name="SLABNO")
	public int getSlabNo() {
		return slabNo;
	}
	public void setSlabNo(int slabNo) {
		this.slabNo = slabNo;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(",INTSLABID="+interestSlabId);
		stringBuilder.append(",CODE="+interestSlabCode);
		stringBuilder.append(",NODAYSFROM="+noOfDaysFrom);
		stringBuilder.append(",NODAYSTO="+noOfDaysTo);
		stringBuilder.append(",PRODUCTID="+productId);
		stringBuilder.append(",RATE="+rate);
		stringBuilder.append(",SLABNO="+slabNo);
		return stringBuilder.toString();
	}
}
