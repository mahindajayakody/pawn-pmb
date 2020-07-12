package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.QueryCriteria;
import com.m4.pawning.dao.SystemProgramDAO;
import com.m4.pawning.domain.SystemProgram;
import com.m4.pawning.service.SystemProgramService;

public class SystemProgramServiceImpl implements SystemProgramService {
	private SystemProgramDAO systemProgramDAO;

	public void setSystemProgramDAO(SystemProgramDAO systemProgramDAO) {
		this.systemProgramDAO = systemProgramDAO;
	}

	public List<SystemProgram> getAllSystemPrograms(List<QueryCriteria> criteriaList) throws PawnException {		
		return systemProgramDAO.getAllSystemPrograms(criteriaList);
	}
}
