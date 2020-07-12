package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.GlAccount;

public interface AccountService extends AuthorizableService{
	public void createAccount(UserConfig userConfig,GlAccount account)throws PawnException;
	public void updateAccount(UserConfig userConfig,GlAccount account)throws PawnException;
	public void removeAccount(UserConfig userConfig,GlAccount account)throws PawnException;
	
	public GlAccount getAccountById(UserConfig userConfig,int recordId)throws PawnException;
	public GlAccount getAccountByCode(UserConfig userConfig,String accountCode)throws PawnException;
	public DataBag getAllAccount(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;


}
