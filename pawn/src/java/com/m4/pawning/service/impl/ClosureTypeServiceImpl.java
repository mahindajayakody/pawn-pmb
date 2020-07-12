package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ClosureTypeDAO;
import com.m4.pawning.domain.ClosureType;
import com.m4.pawning.service.ClosureTypeService;

public class ClosureTypeServiceImpl extends AuthorizableServiceImpl implements ClosureTypeService{
	private ClosureTypeDAO closureTypeDAO;

	public void setClosureTypeDAO(ClosureTypeDAO closureTypeDAO) {
		this.closureTypeDAO = closureTypeDAO;
	}

	public void createClosureType(UserConfig userConfig, ClosureType closureType) throws PawnException {
		closureTypeDAO.createClosureType(userConfig, closureType);
	}

	public void removeClosureType(UserConfig userConfig, ClosureType closureType) throws PawnException {
		closureTypeDAO.removeClosureType(userConfig, closureType);
	}

	public void updateClosureType(UserConfig userConfig, ClosureType closureType) throws PawnException {
		closureTypeDAO.updateClosureType(userConfig, closureType);
	}

	public DataBag getAllClosureType(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException {
		return closureTypeDAO.getAllClosureType(userConfig, criteriaList);
	}

	public ClosureType getClosureTypeByCode(UserConfig userConfig, String code) throws PawnException {
		return closureTypeDAO.getClosureTypeByCode(userConfig, code);
	}

	public ClosureType getClosureTypeById(UserConfig userConfig, int recordId) throws PawnException {
		return closureTypeDAO.getClosureTypeById(userConfig, recordId);
	}

}
