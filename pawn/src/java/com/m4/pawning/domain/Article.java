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
@Table(name="tblarticle")
public class Article extends Consolidate implements Auditable{
	private int articleId;
	private int productId;
	private int articleModelId;
	private String code;
	private String description;
	private int isActive;

	@Transient
	public int getRecordId() {		
		return articleId;
	}
	public void setRecordId(int recordId) {
		this.articleId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Article")
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ARTICALID")
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	
	@Column(name="ARTICALMODELID")
	public int getArticleModelId() {
		return articleModelId;
	}
	public void setArticleModelId(int articleModelId) {
		this.articleModelId = articleModelId;
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
		stringBuilder.append("ARTICALID=" + articleId);
		stringBuilder.append(",ARTICALMODELID=" + articleModelId);
		stringBuilder.append(",CODE=" + code);
		stringBuilder.append(",DESCRIPTION=" + description);
		stringBuilder.append(",ISACTIVE=" + isActive);
		stringBuilder.append(",PRODUCTID=" + productId);
		return stringBuilder.toString();
	}
}
