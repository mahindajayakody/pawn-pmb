package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AccountDAO;
import com.m4.pawning.domain.GlAccount;

public class AccountDAOImpl extends MasterDAOSupport implements AccountDAO {
	private static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

	public void createAccount(UserConfig userConfig, GlAccount account) {
		logger.debug("**** Enter in to createAccount method ****");
		Criteria criteria = getSession().createCriteria(GlAccount.class);
		criteria.add(Restrictions.eq("code", account.getAccountCode()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

        //initializeDomainObject(userConfig, account);
        //getHibernateTemplate().save(account);
        create(userConfig, account);
		logger.debug("**** Leaving from createAccount method ****");
	}

	public void removeAccount(UserConfig userConfig, GlAccount account) {
		logger.debug("**** Enter in to removeAccount method ****");
//		GlAccount acc = (GlAccount)getHibernateTemplate().get(GlAccount.class, Integer.valueOf(account.getRecordId()));
//		acc.setVersion(account.getVersion());
		remove(userConfig, account);
		//getHibernateTemplate().delete(acc);
		logger.debug("**** Leaving from removeAccount method ****");
	}

	public void updateAccount(UserConfig userConfig, GlAccount account) {
		logger.debug("**** Enter in to updateAccount method ****");
		//initializeDomainObject(userConfig, account);
		//getHibernateTemplate().update(account);
		update(userConfig, account);
		logger.debug("**** Leaving from updateAccount method ****");
	}

	public GlAccount getAccountByCode(UserConfig userConfig, String accountCode) {
		logger.debug("**** Enter in to getAccountByCode method ****");
		GlAccount account = null;
		Criteria criteria = getSession().createCriteria(GlAccount.class);
		criteria.add(Restrictions.eq("accountCode", accountCode));
		account = (GlAccount)criteria.uniqueResult();

		if(account == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getArticleByCode method ****");
		return account;
	}

	public GlAccount getAccountById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getAccountById method ****");
		GlAccount account = null;
		account = (GlAccount)getHibernateTemplate().get(GlAccount.class, Integer.valueOf(recordId));

		if(account == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getAccountById method ****");
		return account;
	}

	public DataBag getAllAccount(UserConfig userConfig,List<QueryCriteria> queryCriteria) {
		logger.debug("**** Enter in to getAllAccount method ****");
		DataBag accountBag = null;
		Criteria criteria = getSession().createCriteria(GlAccount.class);
		accountBag = getDataList(getHibernateTemplate(), queryCriteria,criteria );
		logger.debug("**** Leaving from getAllAccount method ****");
		return accountBag;
	}
}
