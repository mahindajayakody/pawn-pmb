package com.m4.core.bean;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Consolidate extends Trace{
	private int companyId;
	private int branchId;
	private Boolean isBranchExplycit = false;

	
	@Column(name="BRANCHID")
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the isBranchExplycit
	 */
	@Transient
	public Boolean getIsBranchExplycit() {
		return isBranchExplycit;
	}
	/**
	 * @param isBranchExplycit the isBranchExplycit to set
	 */
	public void setIsBranchExplycit(Boolean isBranchExplycit) {
		this.isBranchExplycit = isBranchExplycit;
	}

	
}
