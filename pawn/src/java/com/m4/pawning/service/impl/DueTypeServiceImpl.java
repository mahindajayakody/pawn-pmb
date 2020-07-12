package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.DueTypeDAO;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.service.DueTypeService;

public class DueTypeServiceImpl extends AuthorizableServiceImpl implements DueTypeService {
	private DueTypeDAO dueTypeDAO;

	public void setDueTypeDAO(DueTypeDAO dueTypeDAO) {
		this.dueTypeDAO = dueTypeDAO;
	}

	public void createDueType(UserConfig userConfig, DueType dueType) throws PawnException {
		dueTypeDAO.createDueType(userConfig, dueType);
	}

	public void removeDueType(UserConfig userConfig, DueType dueType)throws PawnException {
		dueTypeDAO.removeDueType(userConfig, dueType);
	}

	public void updateDueType(UserConfig userConfig, DueType dueType)throws PawnException {
		dueTypeDAO.updateDueType(userConfig, dueType);
	}

	public DataBag getAllDueType(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return dueTypeDAO.getAllDueType(userConfig, criteriaList);
	}

	public DueType getDueTypeByCode(UserConfig userConfig, String code)throws PawnException {
		return dueTypeDAO.getDueTypeByCode(userConfig, code);
	}

	public DueType getDueTypeById(UserConfig userConfig, int recordId)throws PawnException {
		return dueTypeDAO.getDueTypeById(userConfig, recordId);
	}
}
