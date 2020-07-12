package com.m4.core.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.m4.core.bean.AccessBranch;

public class UserConfig implements Serializable {
    private int userId ;
    private int branchId ;
    private int companyId ;
    private int authorizeMode;
    private Date loginDate;
    private int userGroupId;
    private String loginName;
    private boolean fake = false;
    private String branchName;
    private String userLogId; 
    private int pawnerId;
    private String brachEmail;
    private String userEmail;
    private List<AccessBranch> accessBranchList = new ArrayList<AccessBranch>();
    private int isActive;
    private int passwordRepeatTime;
    private int passwordExpirePeriod;
    private boolean passwordExpired;
    
    
    
    public String getUserLogId() {
		return userLogId;
	}
	public void setUserLogId(String userLogId) {
		this.userLogId = userLogId;
	}
	public int getAuthorizeMode() {
        return authorizeMode;
    }
    public void setAuthorizeMode(int authorizeMode) {
        this.authorizeMode = authorizeMode;
    }
    public int getBranchId() {
        return branchId;
    }
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Date getLoginDate() {
        return loginDate;
    }
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}
	public boolean isFake() {
		return fake;
	}
	public void setFake(boolean fake) {
		this.fake = fake;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getPawnerId() {
		return pawnerId;
	}
	public void setPawnerId(int pawnerId) {
		this.pawnerId = pawnerId;
	} 
	public List<AccessBranch> getAccessBranchList() {
		return accessBranchList;
	}
	public void setAccessBranchList(List<AccessBranch> accessBranchList) {
		this.accessBranchList = accessBranchList;
	}
	public String getBrachEmail() {
		return brachEmail;
	}
	public void setBrachEmail(String brachEmail) {
		this.brachEmail = brachEmail;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	/**
	 * @return the isActive
	 */
	public int getIsActive() {
		return isActive;
	}
	public int getPasswordRepeatTime() {
		return passwordRepeatTime;
	}
	public void setPasswordRepeatTime(int passwordRepeatTime) {
		this.passwordRepeatTime = passwordRepeatTime;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getPasswordExpirePeriod() {
		return passwordExpirePeriod;
	}
	public void setPasswordExpirePeriod(int passwordexpired) {
		this.passwordExpirePeriod = passwordexpired;
	}
	public boolean getPasswordExpired() {
		return passwordExpired;
	}
	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}	
	
	
}
