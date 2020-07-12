package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.PawnerType;
import com.m4.core.util.QueryCriteria;

public interface PawnerTypeDAO extends AuthorizableDAO{

	public void createPawnerType(UserConfig userconfig,PawnerType pawnerType);
	public void modifyPawnerType(UserConfig userconfig,PawnerType pawnerType);
	public void deletePawnerType(UserConfig userconfig,PawnerType pawnerType);

	public PawnerType getPawnerTypeById(UserConfig userconfig,int recordId);
	public PawnerType getPawnerTypeByCode(UserConfig userconfig,String code);
	public DataBag getAllPawnerType(UserConfig userconfig,List<QueryCriteria> criteriaList );


}
