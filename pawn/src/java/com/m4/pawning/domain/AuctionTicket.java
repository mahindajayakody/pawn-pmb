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

import org.hibernate.sql.Update;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;
import com.m4.core.bean.Trace;
import com.m4.core.bean.TransactionConsolidate;
@Table
@Entity(name="tblauctionticket")
public class AuctionTicket extends Consolidate implements Auditable{
	private int auctionTicketId;
	private int ticketId;
	private int auctionId;
	private int productId;
	private Date assignDate;
	private Date soldDate;
	private double minimumCapital;
	private double minimumInterest;
	private double soldAmount;
	private double capitalSettled;
	private double interestSettled;
	private double auctionExpences;
	private int isPaidToClient;
	private double paidAmount;
	private int isAllocated;
	private double excessAmount;
	private Double upsetValue;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="AuctionTicket")

    @Column(name="AUCTIONTKTID")
	public int getAuctionTicketId() {
		return auctionTicketId;
	}

	public void setAuctionTicketId(int auctionTicketId) {
		this.auctionTicketId = auctionTicketId;
	}

	@Column(name="TKTID")
	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

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

	@Column(name="ASSIGNDATE")
	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	@Column(name="SOLDDATE")
	public Date getSoldDate() {
		return soldDate;
	}

	public void setSoldDate(Date soldDate) {
		this.soldDate = soldDate;
	}

	@Column(name="MINCAP")
	public double getMinimumCapital() {
		return minimumCapital;
	}

	public void setMinimumCapital(double minimumCapital) {
		this.minimumCapital = minimumCapital;
	}

	@Column(name="MININT")
	public double getMinimumInterest() {
		return minimumInterest;
	}

	public void setMinimumInterest(double minimumInterest) {
		this.minimumInterest = minimumInterest;
	}

	@Column(name="SOLDAMT")
	public double getSoldAmount() {
		return soldAmount;
	}

	public void setSoldAmount(double soldAmount) {
		this.soldAmount = soldAmount;
	}

	@Column(name="CAPSET")
	public double getCapitalSettled() {
		return capitalSettled;
	}

	public void setCapitalSettled(double capitalSettled) {
		this.capitalSettled = capitalSettled;
	}

	@Column(name="INTSET")
	public double getInterestSettled() {
		return interestSettled;
	}

	public void setInterestSettled(double interestSettled) {
		this.interestSettled = interestSettled;
	}

	@Column(name="AUCTIONEXP")
	public double getAuctionExpences() {
		return auctionExpences;
	}

	public void setAuctionExpences(double auctionExpences) {
		this.auctionExpences = auctionExpences;
	}

	@Column(name="ISPAIDTOCLIENT")
	public int getIsPaidToClient() {
		return isPaidToClient;
	}

	public void setIsPaidToClient(int isPaidToClient) {
		this.isPaidToClient = isPaidToClient;
	}

	@Column(name="PAIDAMT")
	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Column(name="ISALLECATED")
	public int getIsAllocated() {
		return isAllocated;
	}

	public void setIsAllocated(int isAllocated) {
		this.isAllocated = isAllocated;
	}

	@Column(name="EXCESS")
	public double getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(double excessAmount) {
		this.excessAmount = excessAmount;
	}

	@Transient
	public int getRecordId() {
		return auctionTicketId;
	}
	
	public void setRecordId(int recordId) {
		auctionTicketId=recordId;

	}

	/**
	 * @return the upsetValue
	 */
	@Column(name="UPSETVALUE")
	public Double getUpsetValue() {
		return upsetValue;
	}

	/**
	 * @param upsetValue the upsetValue to set
	 */
	public void setUpsetValue(Double upsetValue) {
		this.upsetValue = upsetValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((assignDate == null) ? 0 : assignDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(auctionExpences);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + auctionId;
		result = prime * result + auctionTicketId;
		temp = Double.doubleToLongBits(capitalSettled);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(excessAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(interestSettled);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + isAllocated;
		result = prime * result + isPaidToClient;
		temp = Double.doubleToLongBits(minimumCapital);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minimumInterest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(paidAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + productId;
		temp = Double.doubleToLongBits(soldAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((soldDate == null) ? 0 : soldDate.hashCode());
		result = prime * result + ticketId;
		result = prime * result
				+ ((upsetValue == null) ? 0 : upsetValue.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AuctionTicket)) {
			return false;
		}
		AuctionTicket other = (AuctionTicket) obj;
		if (assignDate == null) {
			if (other.assignDate != null) {
				return false;
			}
		} else if (!assignDate.equals(other.assignDate)) {
			return false;
		}
		if (Double.doubleToLongBits(auctionExpences) != Double
				.doubleToLongBits(other.auctionExpences)) {
			return false;
		}
		if (auctionId != other.auctionId) {
			return false;
		}
		if (auctionTicketId != other.auctionTicketId) {
			return false;
		}
		if (Double.doubleToLongBits(capitalSettled) != Double
				.doubleToLongBits(other.capitalSettled)) {
			return false;
		}
		if (Double.doubleToLongBits(excessAmount) != Double
				.doubleToLongBits(other.excessAmount)) {
			return false;
		}
		if (Double.doubleToLongBits(interestSettled) != Double
				.doubleToLongBits(other.interestSettled)) {
			return false;
		}
		if (isAllocated != other.isAllocated) {
			return false;
		}
		if (isPaidToClient != other.isPaidToClient) {
			return false;
		}
		if (Double.doubleToLongBits(minimumCapital) != Double
				.doubleToLongBits(other.minimumCapital)) {
			return false;
		}
		if (Double.doubleToLongBits(minimumInterest) != Double
				.doubleToLongBits(other.minimumInterest)) {
			return false;
		}
		if (Double.doubleToLongBits(paidAmount) != Double
				.doubleToLongBits(other.paidAmount)) {
			return false;
		}
		if (productId != other.productId) {
			return false;
		}

		if (Double.doubleToLongBits(soldAmount) != Double
				.doubleToLongBits(other.soldAmount)) {
			return false;
		}
		if (soldDate == null) {
			if (other.soldDate != null) {
				return false;
			}
		} else if (!soldDate.equals(other.soldDate)) {
			return false;
		}
		if (ticketId != other.ticketId) {
			return false;
		}
		if (upsetValue == null) {
			if (other.upsetValue != null) {
				return false;
			}
		} else if (!upsetValue.equals(other.upsetValue)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("auctionTicketId=" + auctionTicketId);
		stringBuilder.append(",ticketId=" + ticketId);
		stringBuilder.append(",productId=" + productId);
		stringBuilder.append(",assignDate=" + assignDate);
		stringBuilder.append(",soldDate=" + soldDate);
		stringBuilder.append(",minimumCapital=" + minimumCapital);
		stringBuilder.append(",minimumInterest=" + minimumInterest);
		stringBuilder.append(",auctionExpences=" + auctionExpences);
		stringBuilder.append(",isPaidToClient=" + isPaidToClient);
		stringBuilder.append(",paidAmount=" + paidAmount);
		stringBuilder.append(",isAllocated=" + isAllocated);
		stringBuilder.append(",excessAmount=" + excessAmount);
		stringBuilder.append(",upsetValue=" + upsetValue);
		
		return stringBuilder.toString();
	}
}
