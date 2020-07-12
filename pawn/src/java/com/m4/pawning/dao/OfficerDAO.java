package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.OfficerHistory;

public interface OfficerDAO extends AuthorizableDAO{
	public void createOfficer(UserConfig userConfig,Officer officer);
	public void updateOfficer(UserConfig userConfig,Officer officer);
	public void removeOfficer(UserConfig userConfig,Officer officer);
	public void resetPassword(UserConfig userConfig,Officer officer);
	public void changePassword(UserConfig userConfig, Officer officer);
	
	public Officer getOfficerById(UserConfig userConfig,int recordId);
	public Officer getOfficerByUserName(UserConfig userConfig,String code);
	public DataBag getAllOfficer(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public Officer getOfficerByPawnerId(UserConfig userConfig,int pawnerId);
	public List<OfficerHistory> validatePreviousPassword(Officer officer);
	public List<Officer> getAllActiveOfficer(UserConfig userConfig);
	
}
