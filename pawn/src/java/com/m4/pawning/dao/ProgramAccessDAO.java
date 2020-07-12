package com.m4.pawning.dao;

import java.util.Collection;
import java.util.List;

import com.m4.core.bean.UserLog;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ProgramAccess;
import com.m4.pawning.dto.ProgramAccessDTO;

public interface ProgramAccessDAO {
	public boolean createOrUpdateOfficerAccess(UserConfig userConfig,List<ProgramAccess> accessList);
	public Collection<ProgramAccessDTO> getAllProgramsWithAccessByGroupId(int userGroupId);
	public UserConfig validateUser(int companyId,String userName,String password,String sessionId);
	public void logOut(UserConfig userConfig);
	public Boolean isActiveUser(int companyId,String userName);
	public Collection<ProgramAccessDTO> getPasswordChange(int userGroupId);
	public List<UserLog> getAllTransactionsForUser(UserLog userLog);
}
