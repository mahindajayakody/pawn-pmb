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

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Persistent;

@Entity
@Table(name="tblbranch")
public class Branch extends Persistent implements Auditable{
	private int branchId;
	private int companyId;
	private String code;
	private String addressline1;
	private String addressline2;
	private String addressline3;
	private String addressline4;
	private String telephoneNo;
	private String faxNo;
	private String taxNo;
	private Date dateInstalled;
	private int isDefault;
	private String receiptAccount;
	private String paymentAccount;
	private int originalRecordId;	
	private Date lastUpdateDate;	
	private int userId;
	private int recordStatus;
	private Double fundAvailable;
	private Double fundLimit;
	private SystemDate systemDate;
	private String barnchName;
	private String email; 
	
	public Branch() {}
	
	public Branch(int branchId) {
		this.branchId = branchId;
	}
	
	@Transient
	public int getRecordId() {		
		return branchId;
	}
	public void setRecordId(int recordId) {		
		this.branchId = recordId;
	}
		
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Branch")
	@Column(name="BRANCHID")
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	@Column(name="ADDLINE1")
	public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}
	
	@Column(name="ADDLINE2")
	public String getAddressline2() {
		return addressline2;
	}
	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}
	
	@Column(name="ADDLINE3")
	public String getAddressline3() {
		return addressline3;
	}
	public void setAddressline3(String addressline3) {
		this.addressline3 = addressline3;
	}
	
	@Column(name="ADDLINE4")
	public String getAddressline4() {
		return addressline4;
	}
	public void setAddressline4(String addressline4) {
		this.addressline4 = addressline4;
	}
	
	@Column(name="CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="FAXNO")
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	
	@Column(name="ISDEFAULT")
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	
	@Column(name="LASTUPDATE")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	@Column(name="ORIGINALID")
	public int getOriginalRecordId() {
		return originalRecordId;
	}
	public void setOriginalRecordId(int originalRecordId) {
		this.originalRecordId = originalRecordId;
	}
	
	@Column(name="PMTACC")
	public String getPaymentAccount() {
		return paymentAccount;
	}
	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}
	
	@Column(name="RCPACC")
	public String getReceiptAccount() {
		return receiptAccount;
	}
	public void setReceiptAccount(String receiptAccount) {
		this.receiptAccount = receiptAccount;
	}
	
	@Column(name="RECSTATUS")
	public int getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	@Column(name="TAXNO")
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	
	@Column(name="TPNO")
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	
	@Column(name="LASTUPUSERID")
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Column(name="DATEINSTALL")
	public Date getDateInstalled() {
		return dateInstalled;
	}
	public void setDateInstalled(Date dateInstalled) {
		this.dateInstalled = dateInstalled;
	}
	@Column(name="FUNDAVAILABLE")
	public Double getFundAvailable() {
		return fundAvailable;
	}
	public void setFundAvailable(Double fundAvailable) {
		this.fundAvailable = fundAvailable;
	}
	@Column(name="FUNDLIMIT")
	public Double getFundLimit() {
		return fundLimit;
	}
	public void setFundLimit(Double fundLimit) {
		this.fundLimit = fundLimit;
	}

	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="SYSDATE")
	public SystemDate getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(SystemDate systemDate) {
		this.systemDate = systemDate;
	}

	@Column(name="BRANCHNAME")
	public String getBarnchName() {
		return barnchName;
	}
	public void setBarnchName(String barnchName) {
		this.barnchName = barnchName;
	}
	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("BRANCHID="+branchId);
		stringBuilder.append(",ADDLINE1="+addressline1);
		stringBuilder.append(",ADDLINE2="+addressline2);
		stringBuilder.append(",ADDLINE3="+addressline3);
		stringBuilder.append(",ADDLINE4="+addressline4);
		stringBuilder.append(",CODE="+code);
		stringBuilder.append(",COMID="+companyId);
		stringBuilder.append(",FAXNO="+faxNo);
		stringBuilder.append(",ISDEFAULT="+isDefault);
		stringBuilder.append(",LASTUPDATE="+lastUpdateDate.toString());
		stringBuilder.append(",ORIGINALID="+originalRecordId);
		stringBuilder.append(",PMTACC="+paymentAccount);
		stringBuilder.append(",RCPACC="+receiptAccount);
		stringBuilder.append(",RECSTATUS="+recordStatus);
		stringBuilder.append(",TAXNO="+taxNo);
		stringBuilder.append(",TPNO="+telephoneNo);
		stringBuilder.append(",LASTUPUSERID="+lastUpdateDate.toString());
		stringBuilder.append(",DATEINSTALL="+dateInstalled.toString());
		stringBuilder.append(",FUNDAVAILABLE="+fundAvailable);
		stringBuilder.append(",FUNDLIMIT="+fundLimit);
		stringBuilder.append(",BRANCHNAME="+barnchName);
		return stringBuilder.toString();
	}
	
	
}
