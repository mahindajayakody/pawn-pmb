package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.PawnerType;

public interface PawnerTypeService extends AuthorizableService{
	public void createPawnerType(UserConfig userconfig,PawnerType pawnerType)throws PawnException;
	public void modifyPawnerType(UserConfig userconfig,PawnerType pawnerType)throws PawnException;
	public void deletePawnerType(UserConfig userconfig,PawnerType pawnerType)throws PawnException;

	public PawnerType getPawnerTypeById(UserConfig userconfig,int recordId)throws PawnException;
	public PawnerType getPawnerTypeByCode(UserConfig userconfig,String code)throws PawnException;
	public DataBag getAllPawnerType(UserConfig userconfig,List<QueryCriteria> criteriaList )throws PawnException;
}
