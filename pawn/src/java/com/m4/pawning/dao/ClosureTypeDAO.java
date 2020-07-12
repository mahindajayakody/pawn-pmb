package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ClosureType;

public interface ClosureTypeDAO extends AuthorizableDAO{
	public void createClosureType(UserConfig userConfig,ClosureType closureType);
	public void updateClosureType(UserConfig userConfig,ClosureType closureType);
	public void removeClosureType(UserConfig userConfig,ClosureType closureType);

	public ClosureType getClosureTypeById(UserConfig userConfig,int recordId);
	public ClosureType getClosureTypeByCode(UserConfig userConfig,String code);
	public DataBag getAllClosureType(UserConfig userConfig,List<QueryCriteria> criteriaList);
}
