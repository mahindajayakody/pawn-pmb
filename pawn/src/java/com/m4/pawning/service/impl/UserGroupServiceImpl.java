package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.UserGroupDAO;
import com.m4.pawning.domain.UserGroup;
import com.m4.pawning.service.UserGroupService;

public class UserGroupServiceImpl extends AuthorizableServiceImpl implements UserGroupService {
	private UserGroupDAO userGroupDAO;

	public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
		this.userGroupDAO = userGroupDAO;
	}

	public void createUserGroup(UserConfig userConfig, UserGroup userGroup)throws PawnException {
		userGroupDAO.createUserGroup(userConfig, userGroup);
	}

	public void removeUserGroup(UserConfig userConfig, UserGroup userGroup)throws PawnException {
		userGroupDAO.removeUserGroup(userConfig, userGroup);
	}

	public void updateUserGroup(UserConfig userConfig, UserGroup userGroup)throws PawnException {
		userGroupDAO.updateUserGroup(userConfig, userGroup);
	}

	public DataBag getAllUserGroup(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException {
		return userGroupDAO.getAllUserGroup(userConfig, criteriaList);
	}

	public UserGroup getUserGroupByCode(UserConfig userConfig, String code)throws PawnException {
		return userGroupDAO.getUserGroupByCode(userConfig, code);
	}

	public UserGroup getUserGroupById(UserConfig userConfig, int recordId) throws PawnException {
		return userGroupDAO.getUserGroupById(userConfig, recordId);
	}
}
