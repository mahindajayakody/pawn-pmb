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

import com.m4.core.bean.Auditable;
import com.m4.core.bean.Consolidate;

@Entity
@Table(name="tblmappawner")
public class MapPawner extends Consolidate implements Auditable{
	private int mapPawnerId;
	private int pawnerTypeId;
	private Pawner pawner;	
	private PawnerType pawnerType;
	private int pawnerId;
	
	@Transient
	public int getRecordId() {
		return mapPawnerId;
	}
	public void setRecordId(int recordId) {
		this.mapPawnerId = recordId;
	}
	
	public MapPawner() {}
	
	public MapPawner(int pawnerTypeId) {
		this.pawnerTypeId= pawnerTypeId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="MapPawner")
	@Column(name="MAPPAWID")
	public int getMapPawnerId() {
		return mapPawnerId;
	}
	public void setMapPawnerId(int mapPawnerId) {
		this.mapPawnerId = mapPawnerId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="PAWTYPEID")
	public PawnerType getPawnerType() {
		return pawnerType;
	}
	public void setPawnerType(PawnerType pawnerType) {
		this.pawnerType = pawnerType;
	}

	@Transient
	public int getPawnerTypeId() {
		return pawnerTypeId;
	}
	public void setPawnerTypeId(int pawnerTypeId) {
		this.pawnerTypeId = pawnerTypeId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="PWNID")
	public Pawner getPawner() {
		return pawner;
	}
	public void setPawner(Pawner pawner) {
		this.pawner = pawner;
	}

	@Column(name="TEPAWID")
	public int getPawnerId() {
		return pawnerId;
	}
	public void setPawnerId(int pawnerId) {
		this.pawnerId = pawnerId;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("MAPPAWID="+mapPawnerId);
		stringBuilder.append(",PAWTYPEID="+pawnerTypeId);
		stringBuilder.append(",PWNID="+pawnerId);
		return stringBuilder.toString();
	}
}
