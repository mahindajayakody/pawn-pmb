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
@Table(name="tblofficerHistory")
public class OfficerHistory extends Trace {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int historyId;
	private int officerId;
	private String password;
	
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
    		pkColumnValue="OfficerHistory")
	@Column(name="HISTORYID")
	public int getHistoryId() {
		return historyId;
	}
	/**
	 * @param historyId the historyId to set
	 */
	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
	@Column(name="OFFICERID")
	public int getOfficerId() {
		return officerId;
	}
	/**
	 * @return the historyId
	 */
	
	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}
	
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;	
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + historyId;
		result = prime * result + officerId;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfficerHistory other = (OfficerHistory) obj;
		if (historyId != other.historyId)
			return false;
		if (officerId != other.officerId)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OfficerHistory [historyId=" + historyId + ", officerId="
				+ officerId + ", password=" + password + ", getRecordId()="
				+ getRecordId() + ", getHistoryId()=" + getHistoryId()
				+ ", getOfficerId()=" + getOfficerId() + ", getPassword()="
				+ getPassword() + ", hashCode()=" + hashCode()
				+ ", getOriginalRecordId()=" + getOriginalRecordId()
				+ ", getUserId()=" + getUserId() + ", getLastUpdateDate()="
				+ getLastUpdateDate() + ", getRecordStatus()="
				+ getRecordStatus() + ", getVersion()=" + getVersion()
				+ ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + "]";
	}
	
	
	
}
