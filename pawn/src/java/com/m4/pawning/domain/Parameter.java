package com.m4.pawning.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;
import com.m4.core.bean.Trace;

@Entity
@Table(name="tblparameter")
public class Parameter extends Consolidate implements Auditable{
	private int productId;
	private int parameterId;
	private String code;
	private String description;
	private String isActive;
	private String dataType;

	
	@Transient
	public int getRecordId() {
		return parameterId;
	}	
	public void setRecordId(int recordId) {
		this.parameterId=recordId;

	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Parameter")
	@Column(name="PARAMETERID")
	public int getParameterId(){
		return parameterId;
	}
	public void setParameterId(int parameterId){
		this.parameterId=parameterId;
	}

	@Column(name="PRODUCTID")
	public int getProductId(){
		return productId;
	}
	public void setProductId(int productId){
		this.productId=productId;
	}
	@Column(name="CODE")
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code=code;
	}
	@Column(name="DESCRIPTION")
	public String getDescription(){
		return description;
	}
	public void setDescription(String desc){
		this.description=desc;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	@Column(name="DATATYPE")
	public String getDataType() {
		return dataType;
	}
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("PARAMETERID="+parameterId);
		stringBuilder.append(",PRODUCTID="+productId);
		stringBuilder.append(",CODE="+code);
		stringBuilder.append(",DESCRIPTION="+description);
		stringBuilder.append(",DATATYPE="+dataType);
		stringBuilder.append(",ISACTIVE="+isActive);
		
		return stringBuilder.toString();
	}

}
