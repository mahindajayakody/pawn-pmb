package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Parameter;

public interface ParameterDAO extends AuthorizableDAO{
	public void createParameter(UserConfig userConfig,Parameter parameter);
	public void updateParameter(UserConfig userConfig,Parameter parameter);
	public void removeParameter(UserConfig userConfig,Parameter parameter);

	public Parameter getParameterById(UserConfig userConfig,int recordId);
	public Parameter getParameterByCode(UserConfig userConfig,String code);
	public DataBag getAllParameter(UserConfig userConfig,List<QueryCriteria> criteriaList);

}
