package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Cartage;

public interface CartageDAO extends AuthorizableDAO{
	public void createCartage(UserConfig userConfig,Cartage cartage);
	public void updateCartage(UserConfig userConfig,Cartage cartage);
	public void removeCartage(UserConfig userConfig,Cartage cartage);

	public Cartage getCartageById(UserConfig userConfig,int recordId);
	public Cartage getCartageByCode(UserConfig userConfig,String code);
	public DataBag getAllCartage(UserConfig userConfig,List<QueryCriteria> queryCriteria );
	public Cartage getCartageByCodeAndMasterId(UserConfig userConfig, String code,int cartageMasterId) ;
}
