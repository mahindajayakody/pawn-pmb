package com.m4.pawning.dto;

public class ProgramAccessDTO {
	private int systemProgramId;
	private int programAccessId;
	private String description;
	private int parentId;
	private String url;
	private String access;
	private int version = -1;
	private String defAccess;
	
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getSystemProgramId() {
		return systemProgramId;
	}
	public void setSystemProgramId(int systemProgramId) {
		this.systemProgramId = systemProgramId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}		
    public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public int getProgramAccessId() {
		return programAccessId;
	}
	public void setProgramAccessId(int programAccessId) {
		this.programAccessId = programAccessId;
	}
	public String getDefAccess() {
		return defAccess;
	}
	public void setDefAccess(String defAccess) {
		this.defAccess = defAccess;
	}
}
