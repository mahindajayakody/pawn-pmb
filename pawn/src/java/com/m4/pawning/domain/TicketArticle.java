package com.m4.pawning.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.m4.core.bean.TransactionConsolidate;

@Entity
@Table(name="tblticketarticle")
public class TicketArticle extends TransactionConsolidate{
	private int ticketArticleId;
	private int articleId;
	private int productId;
	private int ticketId;         //
	private int articleModelId;
	private double marcketValue;
	private double assessedValue;
	private double netWeight;
	private double grossWeight;
	private String remarks;       //
	private double articleValue;  //
	private int isActive;         //
	private String articleDescription;
	private int noOfItem;
	private Cartage cartage;
	
	
	@Transient
	public int getRecordId() {
		return ticketArticleId;
	}
	public void setRecordId(int recordId) {
		this.ticketArticleId = recordId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="TicketArticle")
	@Column(name="TICKETARTID")
	public int getTicketArticleId() {
		return ticketArticleId;
	}
	public void setTicketArticleId(int ticketArticleId) {
		this.ticketArticleId = ticketArticleId;
	}
	
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
	
	@Column(name="ARTVALUE")
	public double getArticleValue() {
		return articleValue;
	}
	public void setArticleValue(double articleValue) {
		this.articleValue = articleValue;
	}
	
	@Column(name="ASSVALUE")
	public double getAssessedValue() {
		return assessedValue;
	}
	public void setAssessedValue(double assessedValue) {
		this.assessedValue = assessedValue;
	}
	
	@Column(name="GROSSWEIGHT")
	public double getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}
	
	@Column(name="ISACTIVE")
	public int getIsActive() {
		return isActive;
	}	
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="MARVALUE")
	public double getMarcketValue() {
		return marcketValue;
	}
	public void setMarcketValue(double marcketValue) {
		this.marcketValue = marcketValue;
	}
	
	@Column(name="NETWEIGHT")
	public double getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(double netWeight) {
		this.netWeight = netWeight;
	}
	
	@Column(name="PRODUCTID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {		
		this.productId = productId;
	}
	
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Column(name="TICKETID")
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	@Column(name="ARTDESCRIPTION")
	public String getArticleDescription() {
		return articleDescription;
	}
	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}
	
	@Column(name="NOOFARTICLE")
	public int getNoOfItem() {
		return noOfItem;
	}
	public void setNoOfItem(int noOfItem) {
		this.noOfItem = noOfItem;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CARTAGEID")
	public Cartage getCartage() {
		return cartage;
	}
	public void setCartage(Cartage cartage) {
		this.cartage = cartage;
	}
}
