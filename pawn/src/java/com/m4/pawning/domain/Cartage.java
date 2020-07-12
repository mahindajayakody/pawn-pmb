package com.m4.pawning.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Trace;

@Entity
@Table(name="tblcartage")
public class Cartage extends Trace implements Auditable{
	private int cartageId;
	private int companyId;
	private int productId;
	private int masterCartageId;
	private String code;
	private String Description;
	private Double marcketValue;
	private Double disbursePercentage;
	private Double disburseValue;
	private String isActive;
	private String displayValue;	
	
	public Cartage() {
		super();
	}
	
	public Cartage(int cartageId) {
		super();
		this.cartageId = cartageId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Cartage")
	@Column(name="CARTAGEID")
	public int getCartageId() {
		return cartageId;
	}

	public void setCartageId(int cartageId) {
		this.cartageId = cartageId;
	}
	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Column(name="MCARTAGEID")
	public int getMasterCartageId() {
		return masterCartageId;
	}

	public void setMasterCartageId(int masterCartageId) {
		this.masterCartageId = masterCartageId;
	}
	@Column(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="description")
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	@Column(name="MAKVALUE")
	public Double getMarcketValue() {
		return marcketValue;
	}

	public void setMarcketValue(Double marcketValue) {
		this.marcketValue = marcketValue;
	}
	@Column(name="DISBSEPECR")
	public Double getDisbursePercentage() {
		return disbursePercentage;
	}

	public void setDisbursePercentage(Double disbursePercentage) {
		this.disbursePercentage = disbursePercentage;
	}
	@Column(name="DISBSEVALE")
	public Double getDisburseValue() {
		return disburseValue;
	}
	
	public void setDisburseValue(Double disburseValue) {
		this.disburseValue = disburseValue;
	}
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Transient
	public int getRecordId() {
		return cartageId;
	}
	
	public void setRecordId(int recordId) {
		this.cartageId=recordId;
	}
	@Column(name="DISPLAYVALUE")
	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("CARTAGEID="+cartageId);
		stringBuilder.append("COMID="+companyId);
		stringBuilder.append("PRODUCTID="+productId);
		return super.toString();
	}
	

}
