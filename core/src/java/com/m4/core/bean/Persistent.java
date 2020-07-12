package com.m4.core.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@MappedSuperclass
public abstract class Persistent implements Serializable{
	private int version = -1;

	public Persistent() {
		super();
	}

	@Version
    @Column(name="VERSIONID")
	public int getVersion() {
	    return version;
	}
	public void setVersion(int version) {
	    this.version = version;
	}

	public boolean equals(Object obj) {        
        return EqualsBuilder.reflectionEquals(this,obj);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }    
}
