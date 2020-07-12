package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ParameterValueDAO;
import com.m4.pawning.domain.ParameterValue;


public class ParameterValueDAOImpl extends MasterDAOSupport implements ParameterValueDAO {
	private static final Logger logger= Logger.getLogger(ParameterValueDAOImpl.class);
	
	public void createParameterValue(UserConfig userConfig,ParameterValue parameterValue) {
		logger.debug("**** Enter int to createParameterValue ****");
		//Criteria criteria=getSession().createCriteria(ParameterValue.class);
		//criteria.add(Restrictions.eq("parameterId", parameterValue.getParameterValueId()));
		//criteria.setProjection(Projections.rowCount());
		
		//int count = ((Integer) criteria.uniqueResult()).intValue();
		
		//if (count!=0)
			//throw new CommonDataAccessException("errors.recordexist");
		
		initializeDomainObject(userConfig, parameterValue);
		parameterValue.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		getHibernateTemplate().save(parameterValue);
		logger.debug("**** Leaving createParameterValue ****");
	}

	public DataBag getAllParameterValue(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllParameterValue method ****");
		DataBag parameterValueDataBag;
		Criteria criteria = getSession().createCriteria(ParameterValue.class);
		parameterValueDataBag= getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.debug("**** Leaving getAllParameterValue method ****");
		return parameterValueDataBag;
	}

	public ParameterValue getParameterValueById(UserConfig userConfig,int recordId) {
		logger.debug("**** Enter in to getParameterValueById method ****");
		ParameterValue parameterValue=null;
		parameterValue=(ParameterValue) getHibernateTemplate().get(ParameterValue.class, Integer.valueOf(recordId));
		
		if(parameterValue==null)
			throw new CommonDataAccessException("error.recordnotfound");
		logger.debug("**** Leaving getParameterValueById method ****");
		return parameterValue;
	}

	public void removeParameterValue(UserConfig userConfig,ParameterValue parameterValue) {
		logger.debug("**** Enter in to removeParameterValue method ****");
		ParameterValue paraValue = (ParameterValue)getHibernateTemplate().get(ParameterValue.class, Integer.valueOf(parameterValue.getRecordId()));
		paraValue.setVersion(parameterValue.getVersion());
		getHibernateTemplate().delete(paraValue);
		logger.debug("**** Leaving from removeParameterValue method ****");
	}

	public void updateParameterValue(UserConfig userConfig,ParameterValue parameterValue) {
		logger.debug("**** Enter in to updateParameter method ****");
		initializeDomainObject(userConfig, parameterValue);
		parameterValue.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		getHibernateTemplate().update(parameterValue);
		logger.debug("**** Leaving from updateParameter method ****");		
	}

}
