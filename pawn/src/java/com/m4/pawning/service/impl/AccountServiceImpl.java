package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AccountDAO;
import com.m4.pawning.domain.GlAccount;
import com.m4.pawning.service.AccountService;

public class AccountServiceImpl extends AuthorizableServiceImpl implements AccountService {
	private AccountDAO accountDAO;

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
		//this.authorizableDAO = accountDAO;
	}

	public void createAccount(UserConfig userConfig, GlAccount account)throws PawnException {
		accountDAO.createAccount(userConfig, account);
	}

	public GlAccount getAccountByCode(UserConfig userConfig, String accountCode)throws PawnException {
		return accountDAO.getAccountByCode(userConfig, accountCode);
	}

	public GlAccount getAccountById(UserConfig userConfig, int recordId)throws PawnException {
		return accountDAO.getAccountById(userConfig, recordId);
	}

	public DataBag getAllAccount(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return accountDAO.getAllAccount(userConfig, criteriaList);
	}

	public void removeAccount(UserConfig userConfig, GlAccount account)throws PawnException {
		accountDAO.removeAccount(userConfig, account);
	}

	public void updateAccount(UserConfig userConfig, GlAccount account)throws PawnException {
		accountDAO.updateAccount(userConfig, account);
	}
}
