package com.m4.core.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Consolidate;

@Entity
@Table(name="tbluserlog")
public class UserLog extends Consolidate {
	private int userLogId;
	private int status;
	private String transactionId;
	

	@Transient
	public int getRecordId() {
		return userLogId;
	}

	public void setRecordId(int recordId) {
		userLogId = recordId;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="UserLog")
	@Column(name="USERLOGID")
	public int getUserLogId() {
		return userLogId;
	}
	public void setUserLogId(int userLogId) {
		this.userLogId = userLogId;
	}

	@Column(name="STATUS")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name="TRANNO")
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


}
