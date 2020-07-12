package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.GlAccount;

public interface AccountDAO extends AuthorizableDAO{
	public void createAccount(UserConfig userConfig,GlAccount account);
	public void updateAccount(UserConfig userConfig,GlAccount account);
	public void removeAccount(UserConfig userConfig,GlAccount account);

	public DataBag getAllAccount(UserConfig userConfig,List<QueryCriteria> queryCriteria);
	public GlAccount getAccountById(UserConfig userConfig,int recordId);
	public GlAccount getAccountByCode(UserConfig userConfig,String accountCode);

}
