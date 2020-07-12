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
import com.m4.core.bean.Consolidate;
import  com.m4.core.bean.Trace;

@Entity
@Table(name="tblinterestrates")
public class InterestRates extends Consolidate implements Auditable{

	private int interestId;
	private int productId;
	private String code;
	private String description;
	private int isActive;

	@Transient
	public int getRecordId() {		
		return interestId;
	}
	public void setRecordId(int recordId) {
		this.interestId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="InterestRates")
	@Column(name="INTERESTID")
	public int getInterestRateId() {
		return interestId;
	}
	public void setInterestRateId(int interestId) {
		this.interestId = interestId;
		
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
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(",INTERESTID="+interestId);
		stringBuilder.append(",CODE="+code);
		stringBuilder.append(",DESCRIPTION="+description);
		stringBuilder.append(",ISACTIVE="+isActive);
		stringBuilder.append(",PRODUCTID="+productId);
		
		return super.toString();
	}
	
	
}
