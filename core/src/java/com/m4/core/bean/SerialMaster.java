package com.m4.core.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import com.m4.core.util.BaseSystem;


@Entity
@org.hibernate.annotations.Entity(mutable=false)
@Table(name="serial_master")
public class SerialMaster extends BaseSystem {
	private int serialMasterId;
	private String serialCode;
	private String description;
	private int isBranchWise;
	private int isProductWise;
	private int isAnnually;

	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="SerialMaster")
	@Column(name="SERIALMASTERID")
	public int getSerialMasterId() {
		return serialMasterId;
	}
	public void setSerialMasterId(int serialMasterId) {
		this.serialMasterId = serialMasterId;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="ISBRANCH")
	public int getIsBranchWise() {
		return isBranchWise;
	}
	public void setIsBranchWise(int isBranchWise) {
		this.isBranchWise = isBranchWise;
	}

	@Column(name="CODE")
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	@Column(name="ISPRD")
	public int getIsProductWise() {
		return isProductWise;
	}
	public void setIsProductWise(int isProductWise) {
		this.isProductWise = isProductWise;
	}
	
	@Column(name="ISANNUALLY")
	public int getIsAnnually() {
		return isAnnually;
	}
	public void setIsAnnually(int isAnnually) {
		this.isAnnually = isAnnually;
	}
}
