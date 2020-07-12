package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ClosureType;

public interface ClosureTypeService extends AuthorizableService{
	public void createClosureType(UserConfig userConfig,ClosureType closureType)throws PawnException;
	public void updateClosureType(UserConfig userConfig,ClosureType closureType)throws PawnException;
	public void removeClosureType(UserConfig userConfig,ClosureType closureType)throws PawnException;

	public ClosureType getClosureTypeById(UserConfig userConfig,int recordId)throws PawnException;
	public ClosureType getClosureTypeByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllClosureType(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;

}
