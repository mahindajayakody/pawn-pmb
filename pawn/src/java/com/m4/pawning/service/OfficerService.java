package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Officer;

public interface OfficerService extends AuthorizableService{
	public void createOfficer(UserConfig userConfig,Officer officer)throws PawnException;
	public void updateOfficer(UserConfig userConfig,Officer officer)throws PawnException;
	public void removeOfficer(UserConfig userConfig,Officer officer)throws PawnException;
	public void resetPassword(UserConfig userConfig,Officer officer,String newPassword)throws PawnException;
	public void changePassword(UserConfig userConfig,Officer officer)throws PawnException;

	public Officer getOfficerById(UserConfig userConfig,int recordId)throws PawnException;
	public Officer getOfficerByUserName(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllOfficer(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
	public Officer getOfficerByPawnerId(UserConfig userConfig,int pawnerId)throws PawnException;
}
