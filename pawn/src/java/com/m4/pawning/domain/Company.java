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
import com.m4.core.bean.Persistent;

@Entity
@Table(name="tblusercompany")
public class Company extends Persistent implements Auditable{
	private int companyId;
	private String code;
	private String companyName;
	private String addressline1;
	private String addressline2;
	private String addressline3;
	private String addressline4;
	private String telephoneNo;
	private String faxNo;
	private String taxNo;
	private Date dateInstalled;
	private Date financeBeginDate;
	private Date financeEndDate; 
	private int originalRecordId;	
	private Date lastUpdateDate;	
	private int userId;
	private int recordStatus;
	private int authorizeMode;
	
	@Transient
	public int getRecordId() {		
		return companyId;
	}
	public void setRecordId(int recordId) {		
		this.companyId = recordId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Company")
	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="COMPNAME")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	
	@Column(name="DATEINSTALL")
	public Date getDateInstalled() {
		return dateInstalled;
	}
	public void setDateInstalled(Date dateInstalled) {
		this.dateInstalled = dateInstalled;
	}
	
	@Column(name="FAXNO")
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	
	@Column(name="FBIGDATE")
	public Date getFinanceBeginDate() {
		return financeBeginDate;
	}
	public void setFinanceBeginDate(Date financeBeginDate) {
		this.financeBeginDate = financeBeginDate;
	}
	
	@Column(name="FENDDATE")
	public Date getFinanceEndDate() {
		return financeEndDate;
	}
	public void setFinanceEndDate(Date financeEndDate) {
		this.financeEndDate = financeEndDate;
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
		
	@Column(name="ORIGINALID")
	public int getOriginalRecordId() {
		return originalRecordId;
	}
	public void setOriginalRecordId(int originalRecordId) {
		this.originalRecordId = originalRecordId;
	}
	
	@Column(name="LASTUPUSERID")
	public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    @Column(name="LASTUPDATE")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	@Column(name="RECSTATUS")
	public int getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	@Column(name="AUTHMODE")
	public int getAuthorizeMode() {
		return authorizeMode;
	}
	public void setAuthorizeMode(int authorizeMode) {
		this.authorizeMode = authorizeMode;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder =new StringBuilder();
		stringBuilder.append("COMID="+companyId);
		stringBuilder.append(",CODE="+code);
		stringBuilder.append(",COMPNAME="+companyName);
		stringBuilder.append(",ADDLINE1="+addressline1);
		stringBuilder.append(",ADDLINE2="+addressline2);
		stringBuilder.append(",ADDLINE3="+addressline3);
		stringBuilder.append(",ADDLINE4="+addressline4);
		stringBuilder.append(",DATEINSTALL="+dateInstalled);
		stringBuilder.append(",FAXNO="+faxNo);
		stringBuilder.append(",FBIGDATE="+financeBeginDate);
		stringBuilder.append(",FENDDATE="+financeEndDate);
		stringBuilder.append(",TAXNO="+taxNo);
		stringBuilder.append(",TPNO="+telephoneNo);
		stringBuilder.append(",ORIGINALID="+originalRecordId);
		stringBuilder.append(",LASTUPUSERID="+lastUpdateDate);
		stringBuilder.append(",RECSTATUS="+recordStatus);
		stringBuilder.append(",LASTUPDATE="+lastUpdateDate);
		stringBuilder.append(",AUTHMODE="+authorizeMode);
		return stringBuilder.toString();
	}
}
