package com.m4.pawning.domain;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;
import  com.m4.core.bean.Trace;

@Entity
@Table(name="tblscheme")
public class Schemes extends Consolidate implements Auditable{

	private int schemeId;
	private int interestId;
	private int productId;
	private int cartageMarsterId;
	private String code;
	private String description;
	private int isActive;
	private String interestCode;
	private int period;
//	private Collection<InterestSlab> interestSlads;

	@Transient
	public int getRecordId() {		
		return schemeId;
	}
	public void setRecordId(int recordId) {
		this.schemeId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Schemes")
	@Column(name="SCHEMEID")
	public int getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
		
	}
	
	@Column(name="CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="ISACTIVE")
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Column(name="INTERESTID")
	public int getInterestId() {
		return interestId;
	}
	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}
	@Column(name="MCARTAGEID")
	public int getCartageMarsterId() {
		return cartageMarsterId;
	}
	public void setCartageMarsterId(int cartageMarsterId) {
		this.cartageMarsterId = cartageMarsterId;
	}

	@Column(name="INTERESTCODE")
	public String getInterestCode() {
		return interestCode;
	}
	public void setInterestCode(String interestCode) {
		this.interestCode = interestCode;
	}

	@Column(name="PERIOD")
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("SCHEMEID="+schemeId);
		stringBuilder.append(",CODE="+code);
		stringBuilder.append(",DESCRIPTION="+description);
		stringBuilder.append(",ISACTIVE="+isActive);
		stringBuilder.append(",PRODUCTID="+productId);
		stringBuilder.append(",INTERESTID="+interestId);
		stringBuilder.append(",MCARTAGEID="+cartageMarsterId);
		stringBuilder.append(",INTERESTCODE="+interestCode);
		stringBuilder.append(",PERIOD="+period);
		return stringBuilder.toString();
	}
	
//	@OneToMany(cascade = {CascadeType.ALL},fetch=FetchType.LAZY,mappedBy="interestSlabCode")
//	public Collection<InterestSlab> getInterestSlads() {
//		return interestSlads;
//	}
//	public void setInterestSlads(Collection<InterestSlab> interestSlads) {
//		this.interestSlads = interestSlads;
//	}
}
