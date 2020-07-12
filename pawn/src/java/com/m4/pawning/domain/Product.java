package com.m4.pawning.domain;

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name="tblproduct")
public class Product extends Consolidate implements Auditable{
	private int productid;
	private String productCode;
	private String description;
	private int schemid;
		
	@Transient
	public int getRecordId() {
		return productid;
	}
	public void setRecordId(int recordId) {
		this.productid = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Product")
	@Column(name="PRODUCTID")
	public int getProductId(){
		return productid;
	}
	public void setProductId(int productId){
		this.productid=productId;
	}
	
	
	@Column(name="CODE")
	public String getCode(){
		return productCode;
	}
	public void setCode(String code){
		this.productCode=code;
	}
	
	
	@Column(name="DESCRIPTION")
	public String getDescription(){
		return description;
	}	
	public void setDescription(String descr){
		this.description=descr;
	}
	
	
	@Column(name="SCHEMEID")
	public int getScheme(){
		return schemid;
	}
	public void setScheme(int scheme){
		this.schemid=scheme;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("PRODUCTID="+productid);
		stringBuilder.append(",CODE="+productCode);
		stringBuilder.append(",DESCRIPTION="+description);
		stringBuilder.append(",SCHEMEID="+schemid);
		return stringBuilder.toString();
	}
}
