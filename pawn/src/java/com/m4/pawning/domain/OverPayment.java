package com.m4.pawning.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Trace;

@Entity
@Table(name="tbloverpayment")
public class OverPayment extends Trace {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4960274694793958900L;
	private int overPayId;
	private int productId;
	private int ticketId;
	private int dueTypeId;
	private double ovpAmount;
	private int receiptNo;

	
	@Transient
	public int getRecordId() {
		return overPayId;
	}
	public void setRecordId(int recordId) {
		this.overPayId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="overpay")
	
	@Column(name="OVERPAYID")
	public int getOverPayId() {
		return overPayId;
	}
	public void setOverPayId(int dueFromId) {
		this.overPayId = dueFromId;
	}
	
	
	@Column(name="OVPAMOUNT")
	public double getOvpAmount() {
		return ovpAmount;
	}
	public void setOvpAmount(double ovpAmount) {
		this.ovpAmount = ovpAmount;
	}

	@Column(name="DUETYPEID")
	public int getDueTypeId() {
		return dueTypeId;
	}
	public void setDueTypeId(int dueTypeId) {
		this.dueTypeId = dueTypeId;
	}
	
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	@Column(name="TICKETID")
	public int getTicketId() {
		return ticketId;
	}
	
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	/**
	 * @return the reciptNo
	 */
	@Column(name="RCPID")
	public int getReciptNo() {
		return receiptNo;
	}
	/**
	 * @param reciptNo the reciptNo to set
	 */
	public void setReciptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + dueTypeId;
		result = prime * result + overPayId;
		long temp;
		temp = Double.doubleToLongBits(ovpAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + productId;
		result = prime * result + receiptNo;
		result = prime * result + ticketId;
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
		if (!(obj instanceof OverPayment)) {
			return false;
		}
		OverPayment other = (OverPayment) obj;
		if (dueTypeId != other.dueTypeId) {
			return false;
		}
		if (overPayId != other.overPayId) {
			return false;
		}
		if (Double.doubleToLongBits(ovpAmount) != Double
				.doubleToLongBits(other.ovpAmount)) {
			return false;
		}
		if (productId != other.productId) {
			return false;
		}
		if (receiptNo != other.receiptNo) {
			return false;
		}
		if (ticketId != other.ticketId) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OverPayment [overPayId=" + overPayId + ", productId="
				+ productId + ", ticketId=" + ticketId + ", dueTypeId="
				+ dueTypeId + ", ovpAmount=" + ovpAmount + ", reciptNo="
				+ receiptNo + "]";
	}
	
}
