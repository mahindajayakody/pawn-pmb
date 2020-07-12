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
import com.m4.core.bean.Trace;
@Entity
@Table(name="tblauctionmaster")
public class AuctionMaster extends Trace implements Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int auctionId;
	private int productId;
	private int auctioneeId;
	private int locationId;	
	private String code;
	private String description;
	private int resposiblePerson;
	private int noOfTicket;
	private String status;
	private Double totalCapital;
	private Double totalInterest;
	private Double auctionExpences;
	private Double tax;
	private Double profit;
	private Double totalEarned;
	private Date auctionDate;
	private int companyId;
	private int branchId;
	//private int isCommon;
	
	public AuctionMaster(){
		this.auctionExpences =0.00;
		this.noOfTicket=0;
		this.profit=0.00;
		this.tax=0.00;
		this.totalCapital=0.00;
		this.totalEarned=0.0;
		this.totalInterest=0.00;
	}
		
	@Transient
	public int getRecordId() {
		return auctionId;
	}

	@Override
	public void setRecordId(int recordId) {
		this.auctioneeId=recordId;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="AuctionMaster")
    
    		
 
    @Column(name="AUCTIONID")		
	public int getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(int auctionId) {
		this.auctionId = auctionId;
	}
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Column(name="AUCTIONEEID")
	public int getAuctioneeId() {
		return auctioneeId;
	}

	public void setAuctioneeId(int auctioneeId) {
		this.auctioneeId = auctioneeId;
	}
	@Column(name="CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="BRANCHID")
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="USERID")
	public int getResposiblePerson() {
		return resposiblePerson;
	}

	public void setResposiblePerson(int resposiblePerson) {
		this.resposiblePerson = resposiblePerson;
	}
	@Column(name="NOOFTICKET")
	public int getNoOfTicket() {
		return noOfTicket;
	}

	public void setNoOfTicket(int noOfTicket) {
		this.noOfTicket = noOfTicket;
	}
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="TOTCAP")
	public Double getTotalCapital() {
		return totalCapital;
	}

	public void setTotalCapital(Double totalCapital) {
		this.totalCapital = totalCapital;
	}
	@Column(name="TOTINT")
	public Double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}
	@Column(name="TOTEXPNS")
	public Double getAuctionExpences() {
		return auctionExpences;
	}

	public void setAuctionExpences(Double auctionExpences) {
		this.auctionExpences = auctionExpences;
	}
	@Column(name="TOTTAX")
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}
	@Column(name="PROFIT")
	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}
	@Column(name="TOTEARNED")
	public Double getTotalEarned() {
		return totalEarned;
	}

	public void setTotalEarned(Double totalEarned) {
		this.totalEarned = totalEarned;
	}
	@Column(name="LOCATIONID")
	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	@Column(name="AUCTIONDATE")
	public Date getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}
//	@Column(name="ISCOMMON")
//	public int getIsCommon() {
//		return isCommon;
//	}
//
//	public void setIsCommon(int isCommon) {
//		this.isCommon = isCommon;
//	}

	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("AUCTIONID=" + auctionId);
		stringBuilder.append(",PRODUCTID=" + productId);
		stringBuilder.append(",AUCTIONEEID=" + auctioneeId);
		stringBuilder.append(",CODE=" + code);
		stringBuilder.append(",DESCRIPTION=" + description);
		stringBuilder.append(",USERID=" + resposiblePerson);
		stringBuilder.append(",NOOFTICKET=" + noOfTicket);
		stringBuilder.append(",STATUS=" + status);
		stringBuilder.append(",TOTCAP=" + totalCapital.toString());
		stringBuilder.append(",TOTINT=" + totalInterest.toString());
		stringBuilder.append(",AUCTIONEXP=" + auctionExpences.toString());
		stringBuilder.append(",TAX=" + tax.toString());
		stringBuilder.append(",PROFIT=" + profit.toString());
		stringBuilder.append(",TOTEARNED=" + totalEarned.toString());
		stringBuilder.append(",LOCATIONID=" + locationId);
		stringBuilder.append(",AUCTIONDATE=" + auctionDate);
		return stringBuilder.toString();
	}


}
