package com.m4.pawning.domain;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;

@Entity
@Table(name="tblticket")
public class Ticket extends Consolidate {
//public class Ticket extends Consolidate implements Auditable{
	private int ticketId;
	private int productId;
	//private int pawnerId;
	private Pawner pawner;
	private String ticketNumber;			//
	private String ticketSerialNumber;
	private int schemeId;
	private Date ticketDate;				//
	private Date ticketExpiryDate;			//
	private Date ticketClosedRenewalDate;	//
	private int interestSlabId;
	private int period;
	private int locationId;					//
	private int totalNoOfItems;
	private double totalNetWeight;
	private double totalGrossWeight;
	private double totalAssedValue;
	private double totalMarketValue;
	private double pawnAdvance;
	private double systemAssedValue; 		/*Gold value*/
	private String remark;					//
	private double totalInterestAccrued; 	/*Interest for the period from InterestSlab*/
	private double totalCapitalOutstanding; /*PawnAdvance*/
	private double totalInterestPaid; 		//
	private double totalCapitalPaid;		//
	private int ticketStatusId;				//
	private int isAuctioned;				//
	private double taxAmount1;				//
	private double taxAmount2;				//
	private int renewedTicketNumber;		//
	private int noOfTimesPrinttheTicket;	//
	private int interestMethod;				//
	private int isSchedule;					//
	private int approvedBy;
	private int minDays;
	private Officer officer;
	private Collection<DueFrom> dueFromCollection;
	private Map<String, Reminder> reminderMap;
	private Set<TicketArticle> ticketArticleSet;
	private String pawnAdvInWord;
	private String printTime;


	@Transient
	public int getRecordId() {
		return ticketId;
	}
	public void setRecordId(int recordId) {
		this.ticketId = recordId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Ticket")
	@Column(name="TICKETID")
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	@Column(name="INTMETHOD")
	public int getInterestMethod() {
		return interestMethod;
	}
	public void setInterestMethod(int interestMethod) {
		this.interestMethod = interestMethod;
	}

	@Column(name="INTSLABID")
	public int getInterestSlabId() {
		return interestSlabId;
	}
	public void setInterestSlabId(int interestSlabId) {
		this.interestSlabId = interestSlabId;
	}

	@Column(name="ISAUCTIONED")
	public int getIsAuctioned() {
		return isAuctioned;
	}
	public void setIsAuctioned(int isAuctioned) {
		this.isAuctioned = isAuctioned;
	}

	@Column(name="ISSCHEDULE")
	public int getIsSchedule() {
		return isSchedule;
	}
	public void setIsSchedule(int isSchedule) {
		this.isSchedule = isSchedule;
	}

	@Column(name="LOCATIONID")
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	@Column(name="NOOFPRINT")
	public int getNoOfTimesPrinttheTicket() {
		return noOfTimesPrinttheTicket;
	}
	public void setNoOfTimesPrinttheTicket(int noOfTimesPrinttheTicket) {
		this.noOfTimesPrinttheTicket = noOfTimesPrinttheTicket;
	}

	@Column(name="PAWNADV")
	public double getPawnAdvance() {
		return pawnAdvance;
	}
	public void setPawnAdvance(double pawnAdvance) {
		this.pawnAdvance = pawnAdvance;
	}

	@Column(name="PERIOD")
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}

	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name="REMARKS")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="RTKTNO")
	public int getRenewedTicketNumber() {
		return renewedTicketNumber;
	}
	public void setRenewedTicketNumber(int renewedTicketNumber) {
		this.renewedTicketNumber = renewedTicketNumber;
	}

	@Column(name="SCHEMEID")
	public int getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}

	@Column(name="SYSRASDVALUE")
	public double getSystemAssedValue() {
		return systemAssedValue;
	}
	public void setSystemAssedValue(double systemAssedValue) {
		this.systemAssedValue = systemAssedValue;
	}

	@Column(name="TAXAMT1")
	public double getTaxAmount1() {
		return taxAmount1;
	}
	public void setTaxAmount1(double taxAmount1) {
		this.taxAmount1 = taxAmount1;
	}

	@Column(name="TAXAMT2")
	public double getTaxAmount2() {
		return taxAmount2;
	}
	public void setTaxAmount2(double taxAmount2) {
		this.taxAmount2 = taxAmount2;
	}

	@Column(name="CLODATE")
	public Date getTicketClosedRenewalDate() {
		return ticketClosedRenewalDate;
	}
	public void setTicketClosedRenewalDate(Date ticketClosedRenewalDate) {
		this.ticketClosedRenewalDate = ticketClosedRenewalDate;
	}

	@Column(name="CONDATE")
	public Date getTicketDate() {
		return ticketDate;
	}
	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	@Column(name="EXPRDATE")
	public Date getTicketExpiryDate() {
		return ticketExpiryDate;
	}
	public void setTicketExpiryDate(Date ticketExpiryDate) {
		this.ticketExpiryDate = ticketExpiryDate;
	}

	@Column(name="TKTNO")
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	@Column(name="SERNO")
	public String getTicketSerialNumber() {
		return ticketSerialNumber;
	}
	public void setTicketSerialNumber(String ticketSerialNumber) {
		this.ticketSerialNumber = ticketSerialNumber;
	}

	@Column(name="STSID")
	public int getTicketStatusId() {
		return ticketStatusId;
	}
	public void setTicketStatusId(int ticketStatusId) {
		this.ticketStatusId = ticketStatusId;
	}

	@Column(name="ASSVALUE")
	public double getTotalAssedValue() {
		return totalAssedValue;
	}
	public void setTotalAssedValue(double totalAssedValue) {
		this.totalAssedValue = totalAssedValue;
	}

	@Column(name="TOTCAP")
	public double getTotalCapitalOutstanding() {
		return totalCapitalOutstanding;
	}
	public void setTotalCapitalOutstanding(double totalCapitalOutstanding) {
		this.totalCapitalOutstanding = totalCapitalOutstanding;
	}

	@Column(name="TOTCAPPAID")
	public double getTotalCapitalPaid() {
		return totalCapitalPaid;
	}
	public void setTotalCapitalPaid(double totalCapitalPaid) {
		this.totalCapitalPaid = totalCapitalPaid;
	}

	@Column(name="GROSSWEIGHT")
	public double getTotalGrossWeight() {
		return totalGrossWeight;
	}
	public void setTotalGrossWeight(double totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	@Column(name="TOTINT")
	public double getTotalInterestAccrued() {
		return totalInterestAccrued;
	}
	public void setTotalInterestAccrued(double totalInterestAccrued) {
		this.totalInterestAccrued = totalInterestAccrued;
	}

	@Column(name="TOTINTPAID")
	public double getTotalInterestPaid() {
		return totalInterestPaid;
	}
	public void setTotalInterestPaid(double totalInterestPaid) {
		this.totalInterestPaid = totalInterestPaid;
	}

	@Column(name="MKTVALUE")
	public double getTotalMarketValue() {
		return totalMarketValue;
	}
	public void setTotalMarketValue(double totalMarketValue) {
		this.totalMarketValue = totalMarketValue;
	}

	@Column(name="NETWEIGHT")
	public double getTotalNetWeight() {
		return totalNetWeight;
	}
	public void setTotalNetWeight(double totalNetWeight) {
		this.totalNetWeight = totalNetWeight;
	}

	@Column(name="NOOFITEM")
	public int getTotalNoOfItems() {
		return totalNoOfItems;
	}
	public void setTotalNoOfItems(int totalNoOfItems) {
		this.totalNoOfItems = totalNoOfItems;
	}

	@OneToMany(cascade = {CascadeType.PERSIST},fetch=FetchType.LAZY,mappedBy="ticketId")
	public Collection<DueFrom> getDueFromCollection() {
		return dueFromCollection;
	}
	public void setDueFromCollection(Collection<DueFrom> dueFromCollection) {
		this.dueFromCollection = dueFromCollection;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PWNID")
	public Pawner getPawner() {
		return pawner;
	}
	public void setPawner(Pawner pawner) {
		this.pawner = pawner;
	}

	@Column(name="APPROVEBY")
	public int getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name="MINDAYS")
	public int getMinDays() {
		return minDays;
	}
	public void setMinDays(int minDays) {
		this.minDays = minDays;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USRID")
	public Officer getOfficer() {
		return officer;
	}
	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	@OneToMany(cascade = {CascadeType.PERSIST},fetch=FetchType.LAZY,mappedBy="ticketId")
	@MapKey(name="reminderParaCode")
	public Map<String, Reminder> getReminderMap() {
		return reminderMap;
	}
	public void setReminderMap(Map<String, Reminder> reminderMap) {
		this.reminderMap = reminderMap;
	}

//	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="ticketId")
//	public Set<TicketArticle> getTicketArticleCollection() {
//		return ticketArticleCollection;
//	}
//	public void setTicketArticleCollection(
//			Set<TicketArticle> ticketArticleCollection) {
//		this.ticketArticleCollection = ticketArticleCollection;
//	}

	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="ticketId")
	public Set<TicketArticle> getTicketArticleSet() {
		return ticketArticleSet;
	}
	public void setTicketArticleSet(Set<TicketArticle> ticketArticleSet) {
		this.ticketArticleSet = ticketArticleSet;
	}
	@Column(name="AMOUNTINWORDS")
	public String getPawnAdvInWord() {
		return pawnAdvInWord;
	}
	public void setPawnAdvInWord(String pawnAdvInWord) {
		this.pawnAdvInWord = pawnAdvInWord;
	}
	@Column(name="PRINTEDTIME")
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", productId=" + productId
				+ ", pawner=" + pawner.getPawnerId() + ", ticketNumber=" + ticketNumber
				+ ", ticketSerialNumber=" + ticketSerialNumber + ", schemeId="
				+ schemeId + ", ticketDate=" + ticketDate
				+ ", ticketExpiryDate=" + ticketExpiryDate
				+ ", ticketClosedRenewalDate=" + ticketClosedRenewalDate
				+ ", interestSlabId=" + interestSlabId + ", period=" + period
				+ ", locationId=" + locationId + ", totalNoOfItems="
				+ totalNoOfItems + ", totalNetWeight=" + totalNetWeight
				+ ", totalGrossWeight=" + totalGrossWeight
				+ ", totalAssedValue=" + totalAssedValue
				+ ", totalMarketValue=" + totalMarketValue + ", pawnAdvance="
				+ pawnAdvance + ", systemAssedValue=" + systemAssedValue
				+ ", remark=" + remark + ", totalInterestAccrued="
				+ totalInterestAccrued + ", totalCapitalOutstanding="
				+ totalCapitalOutstanding + ", totalInterestPaid="
				+ totalInterestPaid + ", totalCapitalPaid=" + totalCapitalPaid
				+ ", ticketStatusId=" + ticketStatusId + ", isAuctioned="
				+ isAuctioned + ", taxAmount1=" + taxAmount1 + ", taxAmount2="
				+ taxAmount2 + ", renewedTicketNumber=" + renewedTicketNumber
				+ ", noOfTimesPrinttheTicket=" + noOfTimesPrinttheTicket
				+ ", interestMethod=" + interestMethod + ", isSchedule="
				+ isSchedule + ", approvedBy=" + approvedBy + ", minDays="
				+ minDays + ", officer=" + officer.getOfficerId() + ", dueFromCollection="
				+ dueFromCollection.toString() + ", reminderMap=" + reminderMap.toString()
				+ ", ticketArticleSet=" + ticketArticleSet.toString() + ", pawnAdvInWord="
				+ pawnAdvInWord + "]";
	}
	
}
