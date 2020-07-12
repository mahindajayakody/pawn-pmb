package com.m4.pawning.service;


import java.util.Collection;
import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ProgramAccess;
import com.m4.pawning.dto.ProgramAccessDTO;

public interface ProgramAccessService {
	public boolean createOrUpdateOfficerAccess(UserConfig userConfig,List<ProgramAccess> accessList)throws PawnException;
	public Collection<ProgramAccessDTO> getAllProgramsWithAccessByGroupId(int userGroupId)throws PawnException;
	public UserConfig validateUser(int companyId,String userName,String password, String sessionId)throws PawnException;
	public void logOut(UserConfig userConfig)throws PawnException;
	public Collection<ProgramAccessDTO> getPasswordChange(int userGroupId);
}
