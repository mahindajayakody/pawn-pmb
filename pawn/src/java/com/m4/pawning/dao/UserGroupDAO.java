package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.UserGroup;

public interface UserGroupDAO extends AuthorizableDAO{
	public void createUserGroup(UserConfig userConfig,UserGroup userGroup);
	public void updateUserGroup(UserConfig userConfig,UserGroup userGroup);
	public void removeUserGroup(UserConfig userConfig,UserGroup userGroup);

	public UserGroup getUserGroupById(UserConfig userConfig,int recordId);
	public UserGroup getUserGroupByCode(UserConfig userConfig,String code);
	public DataBag getAllUserGroup(UserConfig userConfig,List<QueryCriteria> criteriaList);
}
