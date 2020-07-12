package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.CartageMarster;

public interface CartageMarsterService extends AuthorizableService{
	public void createCartageMarster(UserConfig userConfig,CartageMarster cartageMarster)throws PawnException;
	public void updateCartageMarster(UserConfig userConfig,CartageMarster cartageMarster)throws PawnException;
	public void removeCartageMarster(UserConfig userConfig,CartageMarster cartageMarster)throws PawnException;

	public CartageMarster getCartageMarsterById(UserConfig userConfig,int recordId)throws PawnException;
	public CartageMarster getCartageMarsterByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllCartageMarster(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;

}
