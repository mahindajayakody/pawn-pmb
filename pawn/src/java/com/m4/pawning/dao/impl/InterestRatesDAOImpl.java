package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.bean.Trace;
import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.InterestRatesDAO;
import com.m4.pawning.domain.InterestRates;
import com.m4.pawning.domain.InterestSlab;

public class InterestRatesDAOImpl extends MasterDAOSupport implements InterestRatesDAO {

	private static final Logger logger = Logger.getLogger(InterestRatesDAOImpl.class);

	public void createInterestRates(UserConfig userConfig, InterestRates interestRates,List<InterestSlab> slabs) {
		logger.debug("**** Enter in to createInterestRates method ****");
		Criteria criteria = getSession().createCriteria(InterestRates.class);
		criteria.add(Restrictions.eq("code", interestRates.getCode()));
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userConfig, interestRates);
//        interestRates.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
//        getHibernateTemplate().save(interestRates);
        create(userConfig, interestRates);

        //Save InterestSlab Data
        for(InterestSlab slab:slabs){
        	initializeDomainObject(userConfig, slab);
        	slab.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
        	slab.setInterestSlabCode(interestRates.getInterestRateId());
        	getHibernateTemplate().save(slab);
        }
		logger.debug("**** Leaving from createInterestRates method ****");
	}

	public void updateInterestRates(UserConfig userConfig, InterestRates interestRates,List<InterestSlab> slabs) {
		logger.debug("**** Enter in to updateInterestRates method ****");
//		interestRates.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
//		initializeDomainObject(userConfig, interestRates);
//		getHibernateTemplate().update(interestRates);
		update(userConfig, interestRates);

		//Delete * related data from InterestSlab if authorizemode single
		if(userConfig.getAuthorizeMode()==0){
			Query deleteQuery = getSession().createQuery("DELETE FROM InterestSlab WHERE interestSlabCode=" + interestRates.getInterestRateId());
			deleteQuery.executeUpdate();
		}
		
		//Save new Records to the InterestSlab
        for(InterestSlab slab:slabs){
        	initializeDomainObject(userConfig, slab);
        	slab.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
        	slab.setInterestSlabCode(interestRates.getInterestRateId());
        	getHibernateTemplate().save(slab);
        }
		logger.debug("**** Leaving from updateInterestRates method ****");
	}

	public DataBag getAllInterestRates(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllInterestRates method ****");
		DataBag interestRatesBag = null;
		Criteria criteria = getSession().createCriteria(InterestRates.class);
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));

		interestRatesBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllInterestRates method ****");
		return interestRatesBag;
	}

	public InterestRates getInterestRateByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getInterestRateByCode method ****");
		InterestRates interestRates = null;
		Criteria criteria = getSession().createCriteria(InterestRates.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		criteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));

		interestRates = (InterestRates)criteria.uniqueResult();
		if(interestRates == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getInterestRateByCode method ****");
		return interestRates;
	}

	public InterestRates getInterestRateById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getInterestRateById method ****");
		InterestRates interestRates = null;
		interestRates = (InterestRates)getHibernateTemplate().get(InterestRates.class, Integer.valueOf(recordId));

		if(interestRates == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getInterestRateById method ****");
		return interestRates;
	}

	public DataBag getAllInterestSlabs(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllInterestSlabs method ****");
		DataBag slabsBag = null;
		Criteria criteria = getSession().createCriteria(InterestSlab.class);
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));

		slabsBag = getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.debug("**** Leaving from getAllInterestSlabs method ****");
		return slabsBag;
	}

}
