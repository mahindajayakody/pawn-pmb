package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.CartageMarster;

public interface CartageMarsterDAO extends AuthorizableDAO{
	public void createCartageMarster(UserConfig userConfig,CartageMarster cartageMarster);
	public void updateCartageMarster(UserConfig userConfig,CartageMarster cartageMarster);
	public void removeCartageMarster(UserConfig userConfig,CartageMarster cartageMarster);

	public CartageMarster getCartageMarsterById(UserConfig userConfig,int recordId);
	public CartageMarster getCartageMarsterByCode(UserConfig userConfig,String code);
	public DataBag getAllCartageMarster(UserConfig userConfig,List<QueryCriteria> criteriaList);

}
