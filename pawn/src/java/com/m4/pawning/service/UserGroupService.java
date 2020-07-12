package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.UserGroup;


public interface UserGroupService extends AuthorizableService{
	public void createUserGroup(UserConfig userConfig,UserGroup userGroup)throws PawnException;
	public void updateUserGroup(UserConfig userConfig,UserGroup userGroup)throws PawnException;
	public void removeUserGroup(UserConfig userConfig,UserGroup userGroup)throws PawnException;

	public UserGroup getUserGroupById(UserConfig userConfig,int recordId)throws PawnException;
	public UserGroup getUserGroupByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllUserGroup(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
}
