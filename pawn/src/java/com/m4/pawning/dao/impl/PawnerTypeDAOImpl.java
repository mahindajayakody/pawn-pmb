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
import com.m4.pawning.dao.PawnerTypeDAO;
import com.m4.pawning.domain.PawnerType;

public class PawnerTypeDAOImpl extends MasterDAOSupport implements PawnerTypeDAO {
	private static final Logger logger=Logger.getLogger(PawnerTypeDAOImpl.class);

	public void createPawnerType(UserConfig userConfig,PawnerType pawnerType) {
		logger.debug("**** Enter in to createPawnerType method ****");
		Criteria criteria=getSession().createCriteria(PawnerType.class);
		criteria.add(Restrictions.eq("code", pawnerType.getCode()));
		criteria.setProjection(Projections.rowCount());

		int count=((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userconfig, pawnerType);
//        getHibernateTemplate().save(pawnerType);
        create(userConfig, pawnerType);
		logger.debug("**** Leaving from createPawnerType method ****");
	}

	public void deletePawnerType(UserConfig userConfig,PawnerType pawnerType) {
		logger.debug("**** Enter in to deletePawnerType method ****");

//		PawnerType pawnerType1= (PawnerType)getHibernateTemplate().get(PawnerType.class, Integer.valueOf(pawnerType.getRecordId()));
//		pawnerType1.setVersion(pawnerType.getVersion());
//		getHibernateTemplate().delete(pawnerType1);
		remove(userConfig, pawnerType);
		logger.debug("**** Leaving from deletePawnerType method ****");

	}

	public void modifyPawnerType(UserConfig userConfig,PawnerType pawnerType) {
		logger.debug("**** Enter in to modifyPawnerType method ****");
//		initializeDomainObject(userConfig, pawnerType);
//		getHibernateTemplate().update(pawnerType);
		update(userConfig, pawnerType);
		logger.debug("**** Leaving from modifyPawnerType method ****");
	}

	public DataBag getAllPawnerType(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllPawnerType method ****");
		DataBag pawnerTypeDB=null;
		Criteria criteria=getSession().createCriteria(PawnerType.class);
		pawnerTypeDB=getDataList(getHibernateTemplate(), criteriaList, criteria);

		logger.debug("**** Leaving from getAllPawnerType method ****");
		return pawnerTypeDB;
	}

	public PawnerType getPawnerTypeByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getPawnerTypeByCode method ****");
		PawnerType pawnerType=null;
		Criteria criteria=getSession().createCriteria(PawnerType.class);
		criteria.add(Restrictions.eq("code", code));
		pawnerType=(PawnerType) criteria.uniqueResult();

		if(pawnerType==null)
			throw new CommonDataAccessException("errors.recordnotfound");


		logger.debug("**** Leaving from getPawnerTypeByCode method ****");
		return pawnerType;
	}

	public PawnerType getPawnerTypeById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getPawnerTypeById method ****");
		PawnerType pawnerType=null;

		pawnerType=	(PawnerType) getHibernateTemplate().get(PawnerType.class, Integer.valueOf(recordId));

		if (pawnerType==null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getPawnerTypeById method ****");
		return pawnerType;
	}
}
