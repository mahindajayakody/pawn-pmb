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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Persistent;


@Entity
@Table(name="tblprgaccess")
public class ProgramAccess extends Persistent implements Auditable{
	private int programAccessId;
	private SystemProgram systemProgram;
	private int userGroupId;
	private String access;
	private Date lastUpdateDate;	
	private int userId;
	private int branchId;
	private int companyId;

	public ProgramAccess() {}
	
	public ProgramAccess(int programAccessId) {
		this.programAccessId = programAccessId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="ProgramAccess")
	@Column(name="PRGACCESSID")
	public int getProgramAccessId() {
		return programAccessId;
	}
	public void setProgramAccessId(int programAccessId) {
		this.programAccessId = programAccessId;
	}
	
	@Column(name="ACCESS")
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRGID")
	public SystemProgram getSystemProgram() {
		return systemProgram;
	}
	public void setSystemProgram(SystemProgram systemProgram) {
		this.systemProgram = systemProgram;
	}
	
	@Column(name="USERGROUPID")
	public int getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
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

	public String toString() {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    } 
}
