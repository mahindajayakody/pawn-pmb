package com.m4.pawning.service.impl;


import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.SchemeDAO;
import com.m4.pawning.domain.Schemes;
import com.m4.pawning.service.SchemeService;

public class SchemeServiceImpl extends AuthorizableServiceImpl implements SchemeService {

private SchemeDAO schemeDAO;

	public void setSchemeDAO(SchemeDAO schemeDAO) {
		this.schemeDAO = schemeDAO;
	}

	public void createScheme(UserConfig userConfig, Schemes  schemes)throws PawnException {
		schemeDAO.createScheme(userConfig, schemes);
	}


	public void updateScheme(UserConfig userConfig, Schemes  schemes)throws PawnException {
		schemeDAO.updateScheme(userConfig, schemes);
	}

	public DataBag getAllScheme(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return schemeDAO.getAllScheme(userConfig, criteriaList);
	}

	public Schemes getSchemeByCode(UserConfig userConfig, String code)throws PawnException {
		return schemeDAO.getSchemeByCode(userConfig, code);
	}

	public Schemes getSchemeById(UserConfig userConfig, int recordId)throws PawnException {
		return schemeDAO.getSchemeById(userConfig, recordId);
	}

	public Schemes getSchemesByCodeAndProductId(UserConfig userConfig, String code, int productId) throws PawnException {
		return schemeDAO.getSchemesByCodeAndProductId(userConfig, code, productId);
	}
	public DataBag getActiveScheme(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return schemeDAO.getActiveScheme(userConfig, criteriaList);
	}
}
