package com.m4.pawning.service;

import java.util.List;
import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Schemes;
public interface SchemeService extends AuthorizableService{
	public void createScheme(UserConfig userConfig,Schemes schemes)throws PawnException;
	public void updateScheme(UserConfig userConfig,Schemes schemes)throws PawnException;

	public Schemes getSchemeById(UserConfig userConfig,int recordId)throws PawnException;
	public Schemes getSchemeByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllScheme(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
	public Schemes getSchemesByCodeAndProductId(UserConfig userConfig,String code,int productId)throws PawnException;
	public DataBag getActiveScheme(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
}
