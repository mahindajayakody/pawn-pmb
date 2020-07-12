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
import com.m4.pawning.dao.DueTypeDAO;
import com.m4.pawning.domain.DueType;


public class DueTypeDAOImpl extends MasterDAOSupport implements DueTypeDAO {
	private static final Logger logger = Logger.getLogger(DueTypeDAOImpl.class);
	
	public void createDueType(UserConfig userConfig, DueType dueType) {
		logger.debug("**** Enter in to createDueType method ****");
		Criteria criteria = getSession().createCriteria(DueType.class);
		criteria.add(Restrictions.eq("dueType", dueType.getDueType()));
		criteria.setProjection(Projections.rowCount());
		
		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");
		
//        initializeDomainObject(userConfig, dueType);
//        getHibernateTemplate().save(dueType);
        create(userConfig, dueType);
		logger.debug("**** Leaving from createDueType method ****");
	}

	public void removeDueType(UserConfig userConfig, DueType dueType) {
		logger.debug("**** Enter in to removeDueType method ****");
//		DueType type = (DueType)getHibernateTemplate().get(DueType.class, Integer.valueOf(dueType.getRecordId()));
//		type.setVersion(dueType.getVersion());
//		getHibernateTemplate().delete(type);
		remove(userConfig, dueType);
		logger.debug("**** Leaving from removeDueType method ****");
	}

	public void updateDueType(UserConfig userConfig, DueType dueType) {
		logger.debug("**** Enter in to updateDueType method ****");
//		initializeDomainObject(userConfig, dueType);
//		getHibernateTemplate().update(dueType);
		update(userConfig, dueType);
		logger.debug("**** Leaving from updateDueType method ****");		
	}
	
	public DataBag getAllDueType(UserConfig userConfig, List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllDueType method ****");
		DataBag dueTypeBag = null;		
		Criteria criteria = getSession().createCriteria(DueType.class);
		dueTypeBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllDueType method ****");
		return dueTypeBag;
	}

	public DueType getDueTypeByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getDueTypeByCode method ****");
		DueType dueType = null;
		Criteria criteria = getSession().createCriteria(DueType.class);
		criteria.add(Restrictions.eq("dueType", code));
		dueType = (DueType)criteria.uniqueResult();
		
		if(dueType == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getDueTypeByCode method ****");
		return dueType;
	}

	public DueType getDueTypeById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getDueTypeById method ****");
		DueType dueType = null;
		dueType = (DueType)getHibernateTemplate().get(DueType.class, Integer.valueOf(recordId));
		
		if(dueType == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getDueTypeById method ****");
		return dueType;
	}
}
