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
import com.m4.pawning.dao.ClosureTypeDAO;
import com.m4.pawning.domain.ClosureType;

public class ClosureTypeDAOImpl extends MasterDAOSupport implements ClosureTypeDAO {
	private static final Logger  logger = Logger.getLogger(ClosureTypeDAOImpl.class);

	public void createClosureType(UserConfig userConfig, ClosureType closureType) {
		logger.debug("**** Enter in to createClosureType method ****");
		Criteria criteria = getSession().createCriteria(ClosureType.class);
		criteria.add(Restrictions.eq("code", closureType.getCode()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

        
//        initializeDomainObject(userConfig, closureType);
//        closureType.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
//        getHibernateTemplate().save(closureType);
        create(userConfig, closureType);
		logger.debug("**** Leaving from createClosureType method ****");

	}

	public void removeClosureType(UserConfig userConfig, ClosureType closureType) {
		logger.debug("**** Enter in to removeClosureType method ****");
//		ClosureType status = (ClosureType)getHibernateTemplate().get(ClosureType.class, Integer.valueOf(closureType.getRecordId()));
//		status.setVersion(closureType.getVersion());
//		getHibernateTemplate().delete(status);
		remove(userConfig, closureType);
		logger.debug("**** Leaving from removeClosureType method ****");
	}

	public void updateClosureType(UserConfig userConfig, ClosureType closureType) {
		logger.debug("**** Enter in to updateClosureType method ****");
//		closureType.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
//		initializeDomainObject(userConfig, closureType);
//		getHibernateTemplate().update(closureType);
		update(userConfig, closureType);
		logger.debug("**** Leaving from updateClosureType method ****");
	}

	public DataBag getAllClosureType(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllClosureType method ****");
		DataBag closureTypeBag = null;
		Criteria criteria = getSession().createCriteria(ClosureType.class);
		closureTypeBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllClosureType method ****");
		return closureTypeBag;
	}

	public ClosureType getClosureTypeByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getClosureTypeByCode method ****");
		ClosureType closureType = null;
		Criteria criteria = getSession().createCriteria(ClosureType.class);
		criteria.add(Restrictions.eq("code", code));
		closureType = (ClosureType)criteria.uniqueResult();

		if(closureType == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getClosureTypeByCode method ****");
		return closureType;
	}

	public ClosureType getClosureTypeById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getClosureTypeById method ****");
		ClosureType closureType = null;
		closureType = (ClosureType)getHibernateTemplate().get(ClosureType.class, Integer.valueOf(recordId));

		if(closureType == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getClosureTypeById method ****");
		return closureType;
	}

}
