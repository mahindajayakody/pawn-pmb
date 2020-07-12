package com.m4.pawning.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.AccessBranch;
import com.m4.core.bean.Auditable;
import com.m4.core.bean.Trace;

@Entity
@Table(name="tblofficer")
public class Officer extends Trace implements Auditable{
	private int officerId;
	private Pawner pawner;
	private Branch branch;
	private UserGroup userGroup;
	private String userName;
	private String password;
	private int companyId;
	private Collection<AccessBranch> accessBranchCollection;
	private int isActive;
	private int passwordRepatTime;
	private Date passwordValidPeriod;

	@Transient
	public int getRecordId() {
		return officerId;
	}
	public void setRecordId(int recordId) {
		this.officerId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Officer")
	@Column(name="OFFICERID")
	public int getOfficerId() {
		return officerId;
	}
	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="DEFBRANCH")	
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;	
	}
	
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="USERGROUP")
	public UserGroup getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
	
	@Column(name="USERNAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="PWNID")
	public Pawner getPawner() {
		return pawner;
	}
	public void setPawner(Pawner pawner) {
		this.pawner = pawner;
	}
	
	@OneToMany(cascade = {CascadeType.PERSIST},fetch=FetchType.LAZY,mappedBy="officerId")
	public Collection<AccessBranch> getAccessBranchCollection() {
		return accessBranchCollection;
	}
	public void setAccessBranchCollection(Collection<AccessBranch> accessBranchCollection) {
		this.accessBranchCollection = accessBranchCollection;
	}
	/**
	 * @return the isActive
	 */
	@Column(name="ISACTIVE")
	public int getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the passwordRepatTime
	 */
	@Column(name="PASSRPTTIME")
	public int getPasswordRepatTime() {
		return passwordRepatTime;
	}
	/**
	 * @param passwordRepatTime the passwordRepatTime to set
	 */
	public void setPasswordRepatTime(int passwordRepatTime) {
		this.passwordRepatTime = passwordRepatTime;
	}
	
	@Column(name="VALIEDPD")
	public Date getPasswordValidPeriod() {
		return passwordValidPeriod;
	}
	public void setPasswordValidPeriod(Date passwordValidPeriod) {
		this.passwordValidPeriod = passwordValidPeriod;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((accessBranchCollection == null) ? 0
						: accessBranchCollection.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + companyId;
		result = prime * result + isActive;
		result = prime * result + officerId;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((pawner == null) ? 0 : pawner.hashCode());
		result = prime * result
				+ ((userGroup == null) ? 0 : userGroup.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Officer other = (Officer) obj;
		if (accessBranchCollection == null) {
			if (other.accessBranchCollection != null)
				return false;
		} else if (!accessBranchCollection.equals(other.accessBranchCollection))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (companyId != other.companyId)
			return false;
		if (isActive != other.isActive)
			return false;
		if (officerId != other.officerId)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (pawner == null) {
			if (other.pawner != null)
				return false;
		} else if (!pawner.equals(other.pawner))
			return false;
		if (userGroup == null) {
			if (other.userGroup != null)
				return false;
		} else if (!userGroup.equals(other.userGroup))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
}
