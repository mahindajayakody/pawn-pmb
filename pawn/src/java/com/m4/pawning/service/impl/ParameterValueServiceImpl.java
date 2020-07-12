package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ParameterValueDAO;
import com.m4.pawning.domain.ParameterValue;
import com.m4.pawning.service.ParameterValueService;

public class ParameterValueServiceImpl extends AuthorizableServiceImpl implements ParameterValueService {
	private ParameterValueDAO parameterValueDAO;

	public void setParameterValueDAO(ParameterValueDAO parameterValueDAO) {
		this.parameterValueDAO = parameterValueDAO;
	}
	public void createParameterValue(UserConfig userConfig,ParameterValue parameterValue) throws PawnException {
		parameterValueDAO.createParameterValue(userConfig, parameterValue);
	}

	public DataBag getAllParameterValue(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return parameterValueDAO.getAllParameterValue(userConfig, criteriaList);
	}

	public ParameterValue getParameterValueById(UserConfig userConfig,int recordId) throws PawnException {
		return parameterValueDAO.getParameterValueById(userConfig, recordId);
	}

	public void removeParameterValue(UserConfig userConfig,ParameterValue parameterValue) throws PawnException {
		parameterValueDAO.removeParameterValue(userConfig, parameterValue);
	}

	public void updateParameterValue(UserConfig userConfig,ParameterValue parameterValue) throws PawnException {
		parameterValueDAO.updateParameterValue(userConfig, parameterValue);
	}

}
