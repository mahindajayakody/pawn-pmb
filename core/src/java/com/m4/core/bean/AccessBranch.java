package com.m4.core.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name="tblaccbranch")
public class AccessBranch extends Persistent{
	private int accessBranchId;
	private int branchId;
	private int officerId;
	private String branchName;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="AccessBranch")
    @Column(name="ACCBRNCHID")		
	public int getAccessBranchId() {
		return accessBranchId;
	}
	public void setAccessBranchId(int accessBranchId) {
		this.accessBranchId = accessBranchId;
	}
	
	@Column(name="BRANCHID")
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	@Column(name="OFFICERID")
	public int getOfficerId() {
		return officerId;
	}
	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}
	
	@Column(name="BRNCHNAME")
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
