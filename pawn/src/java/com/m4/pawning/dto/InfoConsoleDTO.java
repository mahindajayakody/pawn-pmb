package com.m4.pawning.dto;

import java.util.Date;
import java.util.List;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.TicketArticle;

public class InfoConsoleDTO {
	private int ticketId;
	private String pawnerCode;
	private String pawnerName;
	private String address;
	private double pawnAdvance;
	private double marketValue;
	private double actualDisValue;
	private double totalNetWeight;
	private double totalReceiptAmount;
	private Date ticketDate;
	private Date authorizeDate;
	private Date expiraryDate;
	private Date printedDate;
	private String schemeCode;
	private String interestCode;
	private List<TicketArticle> ticketArticleList;
	private List<DueFrom> dueFromList;
	private String schemeDescription;
	private int interestId;
	private int ticketStatusId;
	private String officerName;
	private String approveUserName;
	private Date ticketCloseDate;
	private int isAuctioned;
	private int period;

	public double getActualDisValue() {
		return actualDisValue;
	}
	public void setActualDisValue(double actualDisValue) {
		this.actualDisValue = actualDisValue;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getAuthorizeDate() {
		return authorizeDate;
	}
	public void setAuthorizeDate(Date authorizeDate) {
		this.authorizeDate = authorizeDate;
	}
	public List<DueFrom> getDueFromList() {
		return dueFromList;
	}
	public void setDueFromList(List<DueFrom> dueFromList) {
		this.dueFromList = dueFromList;
	}
	public Date getExpiraryDate() {
		return expiraryDate;
	}
	public void setExpiraryDate(Date expiraryDate) {
		this.expiraryDate = expiraryDate;
	}
	public String getInterestCode() {
		return interestCode;
	}
	public void setInterestCode(String interestCode) {
		this.interestCode = interestCode;
	}
	public double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
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
	public Date getPrintedDate() {
		return printedDate;
	}
	public void setPrintedDate(Date printedDate) {
		this.printedDate = printedDate;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public List<TicketArticle> getTicketArticleList() {
		return ticketArticleList;
	}
	public void setTicketArticleList(List<TicketArticle> ticketArticleList) {
		this.ticketArticleList = ticketArticleList;
	}
	public Date getTicketDate() {
		return ticketDate;
	}
	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
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
	public String getSchemeDescription() {
		return schemeDescription;
	}
	public void setSchemeDescription(String schemeDescription) {
		this.schemeDescription = schemeDescription;
	}
	public int getInterestId() {
		return interestId;
	}
	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}
	public double getTotalReceiptAmount() {
		return totalReceiptAmount;
	}
	public void setTotalReceiptAmount(double totalReceiptAmount) {
		this.totalReceiptAmount = totalReceiptAmount;
	}
	public int getTicketStatusId() {
		return ticketStatusId;
	}
	public void setTicketStatusId(int ticketStatusId) {
		this.ticketStatusId = ticketStatusId;
	}
	public String getOfficerName() {
		return officerName;
	}
	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}
	public String getApproveUserName() {
		return approveUserName;
	}
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}
	/**
	 * @return the ticketCloseDate
	 */
	public Date getTicketCloseDate() {
		return ticketCloseDate;
	}
	/**
	 * @param ticketCloseDate the ticketCloseDate to set
	 */
	public void setTicketCloseDate(Date ticketCloseDate) {
		this.ticketCloseDate = ticketCloseDate;
	}
	/**
	 * @return the isAuctioned
	 */
	public int getIsAuctioned() {
		return isAuctioned;
	}
	/**
	 * @param isAuctioned the isAuctioned to set
	 */
	public void setIsAuctioned(int isAuctioned) {
		this.isAuctioned = isAuctioned;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
	
}
