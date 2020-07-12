package com.m4.core.util;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/*
 * This is use as the base class for system level domain class 
 * eg.SystemTree,Serial  
 */
@MappedSuperclass
public class BaseSystem implements Serializable {
	
	/*
	 * http://jakarta.apache.org/site/downloads/downloads_commons-lang.cgi
	 * 
	 */
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
