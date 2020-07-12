package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.PawnerTypeDAO;
import com.m4.pawning.domain.PawnerType;
import com.m4.pawning.service.PawnerTypeService;

public class PawnerTypeServiceImpl extends AuthorizableServiceImpl implements PawnerTypeService {
	private PawnerTypeDAO pawnerTypeDAO;

	public void setPawnerTypeDAO(PawnerTypeDAO pawnerTypeDAO){
		this.pawnerTypeDAO=pawnerTypeDAO;
	}

	public void createPawnerType(UserConfig userconfig,PawnerType pawnerType) throws PawnException {
		pawnerTypeDAO.createPawnerType(userconfig, pawnerType);
	}

	public void deletePawnerType(UserConfig userconfig,PawnerType pawnerType) throws PawnException {
		// TODO Auto-generated method stub
		pawnerTypeDAO.deletePawnerType(userconfig, pawnerType);
	}

	public DataBag getAllPawnerType(UserConfig userconfig,List<QueryCriteria> criteriaList) throws PawnException {
		// TODO Auto-generated method stub
		return pawnerTypeDAO.getAllPawnerType(userconfig, criteriaList);
	}

	public PawnerType getPawnerTypeByCode(UserConfig userconfig, String code)throws PawnException {
		// TODO Auto-generated method stub
		return pawnerTypeDAO.getPawnerTypeByCode(userconfig, code);
	}

	public PawnerType getPawnerTypeById(UserConfig userconfig, int recordId)throws PawnException {
		// TODO Auto-generated method stub
		return pawnerTypeDAO.getPawnerTypeById(userconfig, recordId);
	}

	public void modifyPawnerType(UserConfig userconfig,PawnerType pawnerType) throws PawnException {
		// TODO Auto-generated method stub
		pawnerTypeDAO.modifyPawnerType(userconfig, pawnerType);
	}

}
