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

@Entity
@Table(name="tblauctionlocation")
public class AuctionLocation extends Consolidate implements Auditable{
	private int alocationId;
	private String code;
	private String description;
	
	@Transient
	public int getRecordId() {		
		return alocationId;
	}
	public void setRecordId(int recordId) {		
		this.alocationId = recordId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="AuctionLocation")
	@Column(name="ALOCATIONID")
	public int getLocationId() {
		return alocationId;
	}
	public void setLocationId(int locationId) {
		this.alocationId = locationId;
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
	@Override
	public String toString() {
		StringBuilder stringBuilder= new StringBuilder();
		stringBuilder.append("ALOCATIONID=" + alocationId);
		stringBuilder.append(",CODE=" + code);
		stringBuilder.append(",DESCRIPTION=" + description);
		return stringBuilder.toString();
	}

}
