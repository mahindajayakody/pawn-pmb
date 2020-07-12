package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Cartage;

public interface CartageService extends AuthorizableService{
	public void createCartage(UserConfig userConfig,Cartage cartage) throws PawnException;
	public void updateCartage(UserConfig userConfig,Cartage cartage) throws PawnException;
	public void removeCartage(UserConfig userConfig,Cartage cartage) throws PawnException;

	public DataBag getAllCartage(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException;
	public Cartage getCartageById(UserConfig userConfig,int recordId) throws PawnException;
	public Cartage getCartageByCode(UserConfig userConfig,String code) throws PawnException;
	public Cartage getCartageByCodeAndMasterId(UserConfig userConfig, String code,int cartageMasterId) throws PawnException;
}
