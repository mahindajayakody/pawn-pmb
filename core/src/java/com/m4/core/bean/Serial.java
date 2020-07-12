package com.m4.core.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true)
@Table(name="serial")
public class Serial extends Persistent {
	private int serialId;
	private String serialCode;
	private long serialValue;
	private int financeYear;
	private Date financeYearBegin;
	private Date financeYearEnd;
	private int companyId;
	private int branchId;
	private int productId;


	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="BaseSystem")
	@Column(name="SERIALID")
	public int getSerialId() {
		return serialId;
	}
	public void setSerialId(int serialId) {
		this.serialId = serialId;
	}

	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Column(name="FINAYEAR")
	public int getFinanceYear() {
		return financeYear;
	}
	public void setFinanceYear(int financeYear) {
		this.financeYear = financeYear;
	}

	@Column(name="FINABEGDATE")
	public Date getFinanceYearBegin() {
		return financeYearBegin;
	}
	public void setFinanceYearBegin(Date financeYearBegin) {
		this.financeYearBegin = financeYearBegin;
	}

	@Column(name="FINAENDDATE")
	public Date getFinanceYearEnd() {
		return financeYearEnd;
	}
	public void setFinanceYearEnd(Date financeYearEnd) {
		this.financeYearEnd = financeYearEnd;
	}

	@Column(name="CODE")
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	@Column(name="RUNGNO")
	public long getSerialValue() {
		return serialValue;
	}
	public void setSerialValue(long serialValue) {
		this.serialValue = serialValue;
	}

	@Column(name="BRANCHID")
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
