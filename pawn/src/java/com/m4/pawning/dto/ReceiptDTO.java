package com.m4.pawning.dto;

import java.util.Date;
import java.util.List;

import com.m4.pawning.domain.DueFrom;

public class ReceiptDTO {
	private int clientId;
	private String clientCode;
	private String clientName;
	private String address;
	private double totalDueAmount;
	private Date receiptDate;
	private List<DueFrom> dueFromList;	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public List<DueFrom> getDueFromList() {
		return dueFromList;
	}
	public void setDueFromList(List<DueFrom> dueFromList) {
		this.dueFromList = dueFromList;
	}
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public double getTotalDueAmount() {
		return totalDueAmount;
	}
	public void setTotalDueAmount(double totalDueAmount) {
		this.totalDueAmount = totalDueAmount;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}	
}
