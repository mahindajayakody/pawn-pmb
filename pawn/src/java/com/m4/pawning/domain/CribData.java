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
@Table(name="tblcribdata")
public class CribData extends Consolidate {
	private int cribId;
	private int ticketId;
	private int productId;
	private double pawnAdvance;
	private double totalInterestAccrued; 	/*Interest for the period from InterestSlab*/
	private double totalCapitalOutstanding; /*PawnAdvance*/
	private double totalInterestPaid; 		//
	private double totalCapitalPaid;		//
	private int ticketStatusId;				//
	private int isAuctioned;				//
	private double totalOther;				//
	private double totalOtherPaid;				//
	private Date closureDate;
	private Pawner pawner;
	private int lastReceipId; 
	private String branchCode;
	private String ticketNo;
	private int period;
	private Date grantDate;
	private Date expireDate;
	private double amountGranted;
	private Date lastReceiptDate;
	
	
	@Transient
	public int getRecordId() {
		return cribId;
	}
	public void setRecordId(int recordId) {
		this.cribId = recordId;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="CribId")
	@Column(name="CRIBID")
	public int getCribId() {
		return cribId;
	}
	public void setCribId(int cribId) {
		this.cribId = cribId;
	}

	@Column(name="TICKETID")
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	@Column(name="ISAUCTIONED")
	public int getIsAuctioned() {
		return isAuctioned;
	}
	public void setIsAuctioned(int isAuctioned) {
		this.isAuctioned = isAuctioned;
	}

	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name="STSID")
	public int getTicketStatusId() {
		return ticketStatusId;
	}
	public void setTicketStatusId(int ticketStatusId) {
		this.ticketStatusId = ticketStatusId;
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

	@Column(name="TOTACCINT")
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
	
	@Column(name="TOTOTH")
	public double getTotalOther() {
		return totalOther;
	}
	public void setTotalOther(double totalOther) {
		this.totalOther = totalOther;
	}
	@Column(name="TOTOTHPAID")
	public double getTotalOtherPaid() {
		return totalOtherPaid;
	}
	public void setTotalOtherPaid(double totalOtherPaid) {
		this.totalOtherPaid = totalOtherPaid;
	}
	
	@Column(name="CLOSUREDATE")
	public Date getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PAWNID")
	public Pawner getPawner() {
		return pawner;
	}
	public void setPawner(Pawner pawner) {
		this.pawner = pawner;
	}
	@Column(name="LASTRCPID")
	public int getLastReceipId() {
		return lastReceipId;
	}
	public void setLastReceipId(int lastReceipId) {
		this.lastReceipId = lastReceipId;
	}
	@Column(name="BRANCHCODE")
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	@Column(name="TKTNO")
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	@Column(name="PERIOD")
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	@Column(name="CONDATE")
	public Date getGrantDate() {
		return grantDate;
	}
	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}
	@Column(name="EXPRDATE")
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	@Column(name="PAWNADV")
	public double getAmountGranted() {
		return amountGranted;
	}
	public void setAmountGranted(double amountGranted) {
		this.amountGranted = amountGranted;
	}
	@Column(name="LASTRCPDATE")
	public Date getLastReceiptDate() {
		return lastReceiptDate;
	}
	public void setLastReceiptDate(Date lastReceiptDate) {
		this.lastReceiptDate = lastReceiptDate;
	}
}
