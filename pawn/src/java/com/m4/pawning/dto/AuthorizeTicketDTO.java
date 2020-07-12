package com.m4.pawning.dto;

import java.util.Date;
import java.util.List;

import com.m4.pawning.domain.TicketArticle;

public class AuthorizeTicketDTO {
	private int ticketId;
	private String pawnerCode;
	private String pawnerName;
	private String productCode;
	private String productDescription;
	private String schemCode;
	private String schemDescription;
	private int period;
	private int interestId;
	private int noOfArticle;
	private double goldValue;
	private double pawnAdvance;
	private double marketValue;
	private double actualDisValue;
	private double totalNetWeight;
	private double totGrossWeight;
	private String interestCode;
	private String address;
	private String remark;
	private int version;
	private List<String> interestSlab;
	private List<TicketArticle> ticketArticleList;
	private Date ticketDate;
	private Date expireDtae;


	public double getActualDisValue() {
		return actualDisValue;
	}
	public void setActualDisValue(double actualDisValue) {
		this.actualDisValue = actualDisValue;
	}
	public double getGoldValue() {
		return goldValue;
	}
	public void setGoldValue(double goldValue) {
		this.goldValue = goldValue;
	}
	public String getInterestCode() {
		return interestCode;
	}
	public void setInterestCode(String interestCode) {
		this.interestCode = interestCode;
	}
	public int getInterestId() {
		return interestId;
	}
	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}
	public double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
	public int getNoOfArticle() {
		return noOfArticle;
	}
	public void setNoOfArticle(int noOfArticle) {
		this.noOfArticle = noOfArticle;
	}
	public double getPawnAdvance() {
		return pawnAdvance;
	}
	public void setPawnAdvance(double pawnAdvance) {
		this.pawnAdvance = pawnAdvance;
	}
	public String getPawnerCode() {
		return pawnerCode;
	}
	public void setPawnerCode(String pawnerCode) {
		this.pawnerCode = pawnerCode;
	}
	public String getPawnerName() {
		return pawnerName;
	}
	public void setPawnerName(String pawnerName) {
		this.pawnerName = pawnerName;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getSchemCode() {
		return schemCode;
	}
	public void setSchemCode(String schemCode) {
		this.schemCode = schemCode;
	}
	public String getSchemDescription() {
		return schemDescription;
	}
	public void setSchemDescription(String schemDescription) {
		this.schemDescription = schemDescription;
	}
	public List<TicketArticle> getTicketArticleList() {
		return ticketArticleList;
	}
	public void setTicketArticleList(List<TicketArticle> ticketArticleList) {
		this.ticketArticleList = ticketArticleList;
	}
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public double getTotalNetWeight() {
		return totalNetWeight;
	}
	public void setTotalNetWeight(double totalNetWeight) {
		this.totalNetWeight = totalNetWeight;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getTotGrossWeight() {
		return totGrossWeight;
	}
	public void setTotGrossWeight(double totGrossWeight) {
		this.totGrossWeight = totGrossWeight;
	}
	public List<String> getInterestSlab() {
		return interestSlab;
	}
	public void setInterestSlab(List<String> interestSlab) {
		this.interestSlab = interestSlab;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Date getTicketDate() {
		return ticketDate;
	}
	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}
	public Date getExpireDtae() {
		return expireDtae;
	}
	public void setExpireDtae(Date expireDtae) {
		this.expireDtae = expireDtae;
	}	
}
