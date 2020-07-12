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
@Table(name="tblarticlemodel")
public class ArticleModel extends Consolidate implements Auditable{
	private int articaleModelId;
	private String code;
	private String description;
	private int productId;


	@Transient
	public int getRecordId() {
		return articaleModelId;
	}

	@Override
	public void setRecordId(int recordId) {
		this.articaleModelId=recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="ArticleModel")
	@Column(name="ARTICALMODELID")
	public int getArticaleModelId(){
		return articaleModelId;
	}
	public void setArticaleModelId(int articaleModel){
		this.articaleModelId=articaleModel;
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
	public int getProductId(){
		return productId;
	}
	public void setProductId(int productId){
		this.productId=productId;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("ARTICALMODELID =" + articaleModelId);
		stringBuilder.append(",CODE =" + code);
		stringBuilder.append(",DESCRIPTION =" + description);
		return stringBuilder.toString();
	}
	

}
