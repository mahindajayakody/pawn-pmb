package com.m4.pawning.dao;


import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Schemes;


public interface SchemeDAO extends AuthorizableDAO{
	public void createScheme(UserConfig userConfig,Schemes schemes);
	public void updateScheme(UserConfig userConfig,Schemes schemes);

	public Schemes getSchemeById(UserConfig userConfig,int recordId);
	public Schemes getSchemeByCode(UserConfig userConfig,String code);
	public DataBag getAllScheme(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public Schemes getSchemesByCodeAndProductId(UserConfig userConfig,String code,int productId);
	public DataBag getActiveScheme(UserConfig userConfig,List<QueryCriteria> criteriaList);
}
