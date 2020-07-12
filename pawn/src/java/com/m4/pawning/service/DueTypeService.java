package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.DueType;

public interface DueTypeService extends AuthorizableService{
	public void createDueType(UserConfig userConfig,DueType dueType)throws PawnException;
	public void updateDueType(UserConfig userConfig,DueType dueType)throws PawnException;
	public void removeDueType(UserConfig userConfig,DueType dueType)throws PawnException;

	public DueType getDueTypeById(UserConfig userConfig,int recordId)throws PawnException;
	public DueType getDueTypeByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllDueType(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;

}
