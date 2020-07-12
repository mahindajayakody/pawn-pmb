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
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.CompanyDAO;
import com.m4.pawning.domain.Company;

public class CompanyDAOImpl extends MasterDAOSupport implements CompanyDAO {
	private static final Logger logger = Logger.getLogger(CompanyDAOImpl.class);
	
	public void createCompany(UserConfig userConfig, Company company) {
		logger.debug("**** Enter in to createCompany method ****");
		Criteria criteria = getSession().createCriteria(Company.class);
		criteria.add(Restrictions.eq("code", company.getCode()));
		criteria.setProjection(Projections.rowCount());
		
		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");
		
        company.setUserId(userConfig.getUserId());
        company.setLastUpdateDate((userConfig).getLoginDate());
        company.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
        getHibernateTemplate().save(company);
		logger.debug("**** Leaving from createCompany method ****");
	}

	public void removeCompany(UserConfig userConfig, Company company) {
		logger.debug("**** Enter in to removeCompany method ****");
		Company status = (Company)getHibernateTemplate().get(Company.class, Integer.valueOf(company.getRecordId()));
		status.setVersion(company.getVersion());
		getHibernateTemplate().delete(status);
		logger.debug("**** Leaving from removeCompany method ****");
	}

	public void updateCompany(UserConfig userConfig, Company company) {
		logger.debug("**** Enter in to updateCompany method ****");
		company.setUserId(userConfig.getUserId());
		company.setLastUpdateDate((userConfig).getLoginDate());
		company.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		getHibernateTemplate().update(company);
		logger.debug("**** Leaving from updateCompany method ****");		
	}
	
	public DataBag getAllCompany(UserConfig userConfig, List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllCompany method ****");
		DataBag companyBag = null;		
		Criteria criteria = getSession().createCriteria(Company.class);
		companyBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllCompany method ****");
		return companyBag;
	}

	public Company getCompanyByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getCompanyByCode method ****");
		Company company = null;
		Criteria criteria = getSession().createCriteria(Company.class);
		criteria.add(Restrictions.eq("code", code));
		company = (Company)criteria.uniqueResult();
		
		if(company == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getCompanyByCode method ****");
		return company;
	}

	public Company getCompanyById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getCompanyById method ****");
		Company company = null;
		company = (Company)getHibernateTemplate().get(Company.class, Integer.valueOf(recordId));
		
		if(company == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getCompanyById method ****");
		return company;
	}
}
