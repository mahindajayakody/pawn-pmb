package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ParameterValue;

public interface ParameterValueDAO extends AuthorizableDAO{
	public void createParameterValue(UserConfig userConfig,ParameterValue parameterValue);
	public void updateParameterValue(UserConfig userConfig,ParameterValue parameterValue);
	public void removeParameterValue(UserConfig userConfig,ParameterValue parameterValue);
	public ParameterValue getParameterValueById(UserConfig userConfig,int recordId);
	//public ParameterValue getParameterValueByCode(UserConfig userConfig,String code);
	public DataBag getAllParameterValue(UserConfig userConfig,List<QueryCriteria >criteriaList);



}
