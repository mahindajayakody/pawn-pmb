package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Pawner;

public interface PawnerDAO{
	public void createPawner(UserConfig userConfig,Pawner pawner);
	public void updatePawner(UserConfig userConfig,Pawner pawner);

	public Pawner getPawnerById(UserConfig userConfig,int recordId);
	public Pawner getPawnerByCode(UserConfig userConfig,String code);
	public Pawner getPawnerByIdOrBr(UserConfig userConfig, String idOrBr) ;
	public List<Pawner> getAllPawnerByType(UserConfig userConfig, String type);
	public List<Pawner> getAllPawnerBySQL(UserConfig userConfig, String sql);
	public DataBag getAllPawner(UserConfig userConfig,List<QueryCriteria> criteriaList);
}
