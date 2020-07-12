package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Parameter;

public interface ParameterService extends AuthorizableService{
	public void createParameter(UserConfig userConfig,Parameter parameter)throws PawnException;
	public void updateParameter(UserConfig userConfig,Parameter parameter)throws PawnException;
	public void removeParameter(UserConfig userConfig,Parameter parameter)throws PawnException;

	public Parameter getParameterById(UserConfig userConfig,int recordId)throws PawnException;
	public Parameter getParameterByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllParameter(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;

}
