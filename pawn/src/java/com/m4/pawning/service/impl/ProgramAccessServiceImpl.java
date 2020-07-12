package com.m4.pawning.service.impl;

import java.util.Collection;
import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ProgramAccessDAO;
import com.m4.pawning.domain.ProgramAccess;
import com.m4.pawning.dto.ProgramAccessDTO;
import com.m4.pawning.service.ProgramAccessService;

public class ProgramAccessServiceImpl implements ProgramAccessService {
	private ProgramAccessDAO programAccessDAO;
	
	public void setProgramAccessDAO(ProgramAccessDAO programAccessDAO) {
		this.programAccessDAO = programAccessDAO;
	}
	
	public boolean createOrUpdateOfficerAccess(UserConfig userConfig,List<ProgramAccess> accessList) throws PawnException {
		return programAccessDAO.createOrUpdateOfficerAccess(userConfig,accessList);
	}

	public Collection<ProgramAccessDTO> getAllProgramsWithAccessByGroupId(int userGroupId) throws PawnException {		
		return programAccessDAO.getAllProgramsWithAccessByGroupId(userGroupId);
	}

	public UserConfig validateUser(int companyId, String userName, String password,String sessionId) throws PawnException {
		return programAccessDAO.validateUser(companyId, userName, password,sessionId);
	}

	public void logOut(UserConfig userConfig) throws PawnException {
		this.programAccessDAO.logOut(userConfig);		
	}

	@Override
	public Collection<ProgramAccessDTO> getPasswordChange(int userGroupId) {
		return this.programAccessDAO.getPasswordChange(userGroupId);
	}
}
