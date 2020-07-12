package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Pawner;

public interface PawnerService {
	public void createPawner(UserConfig userConfig,Pawner pawner)throws PawnException;
	public void updatePawner(UserConfig userConfig,Pawner pawner)throws PawnException;

	public Pawner getPawnerById(UserConfig userConfig,int recordId)throws PawnException;
	public Pawner getPawnerByCode(UserConfig userConfig,String code)throws PawnException;
	public Pawner getPawnerByIdOrBr(UserConfig userConfig, String idOrBr)throws PawnException;
	public List<Pawner> getAllPawnerByType(UserConfig userConfig, String type)throws PawnException;
	public List<Pawner> getAllPawnerBySQL(UserConfig userConfig, String sql)throws PawnException;
	public DataBag getAllPawner(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
}
