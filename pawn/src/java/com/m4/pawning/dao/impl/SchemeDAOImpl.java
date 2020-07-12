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
import com.m4.pawning.dao.SchemeDAO;
import com.m4.pawning.domain.Schemes;

public class SchemeDAOImpl extends MasterDAOSupport implements SchemeDAO {

	private static final Logger logger = Logger.getLogger(SchemeDAOImpl.class);

	public void createScheme(UserConfig userConfig, Schemes schemes) {
		logger.debug("**** Enter in to createScheme method ****");
		Criteria criteria = getSession().createCriteria(Schemes.class);		
		criteria.add(Restrictions.eq("code", schemes.getCode()));
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userConfig, schemes);
//        getHibernateTemplate().save(schemes);
        create(userConfig, schemes);
		logger.debug("**** Leaving from createScheme method ****");
	}

	public void updateScheme(UserConfig userConfig, Schemes schemes) {
		logger.debug("**** Enter in to updateScheme method ****");
//		initializeDomainObject(userConfig, schemes);
//		getHibernateTemplate().update(schemes);
		update(userConfig, schemes);
		logger.debug("**** Leaving from updateScheme method ****");
	}

	public DataBag getAllScheme(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllScheme method ****");
		DataBag schemesBag = null;
		Criteria criteria = getSession().createCriteria(Schemes.class);
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
//		criteria.add(Restrictions.eq("isActive", 1));
		schemesBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllScheme method ****");
		return schemesBag;
	}

	public Schemes getSchemeByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getSchemeByCode method ****");
		Schemes schemes = null;
		Criteria criteria = getSession().createCriteria(Schemes.class);
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("isActive", RecordStatusEnum.ACTIVE.getCode()));
		schemes = (Schemes)criteria.uniqueResult();

		if(schemes == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getSchemeByCode method ****");
		return schemes;
	}

	public Schemes getSchemeById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getSchemeById method ****");
		Schemes schemes = null;
		
		schemes = (Schemes)getHibernateTemplate().get(Schemes.class, Integer.valueOf(recordId));

		if(schemes == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getSchemeById method ****");
		return schemes;
	}

	public Schemes getSchemesByCodeAndProductId(UserConfig userConfig,String code,int productId){
		logger.debug("**** Enter in to getSchemesByCodeAndProductId method ****");
		Schemes schemes = null;

		Criteria criteria = getSession().createCriteria(Schemes.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("productId", productId));
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("isActive", RecordStatusEnum.ACTIVE.getCode()));

		schemes = (Schemes)criteria.uniqueResult();

		if(schemes == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getSchemesByCodeAndProductId method ****");
		return schemes;
	}
	public DataBag getActiveScheme(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllScheme method ****");
		DataBag schemesBag = null;
		Criteria criteria = getSession().createCriteria(Schemes.class);
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("isActive", 1));
		schemesBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllScheme method ****");
		return schemesBag;
	}

}
