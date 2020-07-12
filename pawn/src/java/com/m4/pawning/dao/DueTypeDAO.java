package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.DueType;

public interface DueTypeDAO extends AuthorizableDAO{
	public void createDueType(UserConfig userConfig,DueType dueType);
	public void updateDueType(UserConfig userConfig,DueType dueType);
	public void removeDueType(UserConfig userConfig,DueType dueType);

	public DueType getDueTypeById(UserConfig userConfig,int recordId);
	public DueType getDueTypeByCode(UserConfig userConfig,String code);
	public DataBag getAllDueType(UserConfig userConfig,List<QueryCriteria> criteriaList);

}
