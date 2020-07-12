package com.m4.pawning.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblsystemprogram")
public class SystemProgram implements Serializable {
	private int systemProgramId;
	private String description;
	private int parentId;
	private String url;
	private String productCode;
	private String access;

	public SystemProgram() {}
	
	public SystemProgram(int systemProgramId) {
		this.systemProgramId = systemProgramId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PRGID")
	public int getSystemProgramId() {
		return systemProgramId;
	}
	public void setSystemProgramId(int systemProgramId) {
		this.systemProgramId = systemProgramId;
	}
	
	@Column(name="PARENTID")
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	@Column(name="URLPATH")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}		
	
	@Column(name="PRDCODE")
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	} 
	
	@Column(name="NODENAME")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="ACCESS")
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("PRGID="+systemProgramId);
		stringBuilder.append(",PARENTID="+parentId);
		stringBuilder.append(",URLPATH="+url);
		stringBuilder.append(",PRDCODE="+productCode);
		stringBuilder.append(",NODENAME="+description);
		stringBuilder.append(",ACCESS="+access);
		
		return stringBuilder.toString();
	}
}
