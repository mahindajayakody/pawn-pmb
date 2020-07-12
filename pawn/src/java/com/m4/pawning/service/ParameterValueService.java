package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ParameterValue;

public interface ParameterValueService extends AuthorizableService{
	public void createParameterValue(UserConfig userConfig,ParameterValue parameterValue)throws PawnException;
	public void updateParameterValue(UserConfig userConfig,ParameterValue parameterValue)throws PawnException;
	public void removeParameterValue(UserConfig userConfig,ParameterValue parameterValue)throws PawnException;

	public ParameterValue getParameterValueById(UserConfig userConfig,int recordId)throws PawnException;
	public DataBag getAllParameterValue(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;

}
