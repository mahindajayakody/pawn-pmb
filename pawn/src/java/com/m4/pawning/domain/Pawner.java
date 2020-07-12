package com.m4.pawning.domain;

import java.util.Collection;
import java.util.Date;

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
import com.m4.core.bean.Trace;

@Entity
@Table(name="tblpawner")
public class Pawner extends Trace {
//public class Pawner extends Trace implements Auditable{
	private int pawnerId;
	private String corporateIndividual;
	private String title;
	private String initials;
	private String initialsInFull;
	private String surName;
	private Date dateOfBirth;
	private String nationality;
	private int maritalStatus;
	private String idOrBrNo;
	private String passportNo;
	private String drivingLicenseNumber;
	private String homeTelephoneNo;
	private String officeTelephoneNo;
	private String mobileNo;
	private String faxNo;
	private String emailAddress;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private Date introduceDate;
	//private String pawnerTypes;
	private int pawnerStatus;
	private int companyId;
	private int sex;
	private String pawnerCode;
	private Collection<MapPawner> mapPawner;


	@Transient
	public int getRecordId() {
		return pawnerId;
	}
	public void setRecordId(int recordId) {
		this.pawnerId = recordId;
	}

	public Pawner() {
		super();
	}

	public Pawner(int pawnerId) {
		super();
		this.pawnerId = pawnerId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="InvTab")
    @TableGenerator(name="InvTab",	table="ID_GEN",
    		pkColumnName="ID_NAME",
    		valueColumnName="ID_VALUE",
    		allocationSize=1,
    		pkColumnValue="Pawner")
	@Column(name="PWNID")
	public int getPawnerId() {
		return pawnerId;
	}
	public void setPawnerId(int pawnerId) {
		this.pawnerId = pawnerId;
	}

	@Column(name="MAILADDLINE1")
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	@Column(name="MAILADDLINE2")
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	@Column(name="MAILADDLINE3")
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	@Column(name="MAILADDLINE4")
	public String getAddressLine4() {
		return addressLine4;
	}
	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	@Column(name="CORI")
	public String getCorporateIndividual() {
		return corporateIndividual;
	}
	public void setCorporateIndividual(String corporateIndividual) {
		this.corporateIndividual = corporateIndividual;
	}

	@Column(name="BRDATE")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name="DRVLNO")
	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}
	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	@Column(name="EMAIL")
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name="FAXNO")
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	@Column(name="HOMETPNO")
	public String getHomeTelephoneNo() {
		return homeTelephoneNo;
	}
	public void setHomeTelephoneNo(String homeTelephoneNo) {
		this.homeTelephoneNo = homeTelephoneNo;
	}

	@Column(name="IDNO")
	public String getIdOrBrNo() {
		return idOrBrNo;
	}
	public void setIdOrBrNo(String idOrBrNo) {
		this.idOrBrNo = idOrBrNo;
	}

	@Column(name="INITIALS")
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}

	@Column(name="INITIALSFULL")
	public String getInitialsInFull() {
		return initialsInFull;
	}
	public void setInitialsInFull(String initialsInFull) {
		this.initialsInFull = initialsInFull;
	}

	@Column(name="INTDTE")
	public Date getIntroduceDate() {
		return introduceDate;
	}
	public void setIntroduceDate(Date introduceDate) {
		this.introduceDate = introduceDate;
	}

	@Column(name="MARISTS")
	public int getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(int maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name="MOBILENO")
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name="NATIONAL")
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name="OFFICETPNO")
	public String getOfficeTelephoneNo() {
		return officeTelephoneNo;
	}
	public void setOfficeTelephoneNo(String officeTelephoneNo) {
		this.officeTelephoneNo = officeTelephoneNo;
	}

	@Column(name="PPNO")
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	@Column(name="CLSTATUS")
	public int getPawnerStatus() {
		return pawnerStatus;
	}
	public void setPawnerStatus(int pawnerStatus) {
		this.pawnerStatus = pawnerStatus;
	}

	//@Column(name="MAPTYPES")
//	@Transient
//	public String getPawnerTypes() {
//		return pawnerTypes;
//	}
//	public void setPawnerTypes(String pawnerTypes) {
//		this.pawnerTypes = pawnerTypes;
//	}

	@Column(name="NAME")
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}

	@Column(name="CLTITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="COMID")
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Column(name="SEX")
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}


	@OneToMany(cascade = {CascadeType.PERSIST},fetch=FetchType.LAZY,mappedBy="pawnerId")
	public Collection<MapPawner> getMapPawner() {
		return mapPawner;
	}
	public void setMapPawner(Collection<MapPawner> mapPawner) {
		this.mapPawner = mapPawner;
	}

	@Transient
	public String getPawnerName(){
		return title + " " + initials + " " + surName;
	}

	@Column(name="PAWNCODE")
	public String getPawnerCode() {
		return pawnerCode;
	}
	public void setPawnerCode(String pawnerCode) {
		this.pawnerCode = pawnerCode;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("PWNID="+pawnerId);
		stringBuilder.append(",MAILADDLINE1="+addressLine1);
		stringBuilder.append(",MAILADDLINE2="+addressLine2);
		stringBuilder.append(",MAILADDLINE3="+addressLine3);
		stringBuilder.append(",MAILADDLINE4="+addressLine4);
		stringBuilder.append(",CORI="+corporateIndividual);
		stringBuilder.append(",BRDATE="+dateOfBirth);
		stringBuilder.append(",DRVLNO="+drivingLicenseNumber);
		stringBuilder.append(",EMAIL="+emailAddress);
		stringBuilder.append(",FAXNO="+faxNo);
		stringBuilder.append(",HOMETPNO="+homeTelephoneNo);
		stringBuilder.append(",IDNO="+idOrBrNo);
		stringBuilder.append(",INITIALS="+initials);
		stringBuilder.append(",INITIALSFULL="+initialsInFull);
		stringBuilder.append(",INTDTE="+introduceDate);
		stringBuilder.append(",MARISTS="+maritalStatus);
		stringBuilder.append(",MOBILENO="+mobileNo);
		stringBuilder.append(",NATIONAL="+nationality);
		stringBuilder.append(",OFFICETPNO="+officeTelephoneNo);
		stringBuilder.append(",PPNO="+passportNo);
		stringBuilder.append(",CLSTATUS="+pawnerStatus);
		stringBuilder.append(",NAME="+surName);
		stringBuilder.append(",CLTITLE="+title);
		stringBuilder.append(",COMID="+companyId);
		stringBuilder.append(",SEX="+sex);
		stringBuilder.append(",PAWNCODE="+pawnerCode);
		return stringBuilder.toString();
	}
}

