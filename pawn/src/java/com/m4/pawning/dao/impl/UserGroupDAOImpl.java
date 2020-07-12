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
import com.m4.pawning.dao.UserGroupDAO;
import com.m4.pawning.domain.UserGroup;

public class UserGroupDAOImpl extends MasterDAOSupport implements UserGroupDAO {
	private static final Logger logger = Logger.getLogger(UserGroupDAOImpl.class);

	public void createUserGroup(UserConfig userConfig, UserGroup userGroup) {
		logger.debug("**** Enter in to createUserGroup method ****");
		Criteria criteria = getSession().createCriteria(UserGroup.class);
		criteria.add(Restrictions.eq("code", userGroup.getCode()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userConfig, userGroup);
//        getHibernateTemplate().save(userGroup);
        create(userConfig, userGroup);
		logger.debug("**** Leaving from createUserGroup method ****");
	}

	public void removeUserGroup(UserConfig userConfig, UserGroup userGroup) {
		logger.debug("**** Enter in to removeUserGroup method ****");
//		UserGroup status = (UserGroup)getHibernateTemplate().get(UserGroup.class, Integer.valueOf(userGroup.getRecordId()));
//		status.setVersion(userGroup.getVersion());
//		getHibernateTemplate().delete(status);
		remove(userConfig, userGroup);
		logger.debug("**** Leaving from removeUserGroup method ****");
	}

	public void updateUserGroup(UserConfig userConfig, UserGroup userGroup) {
		logger.debug("**** Enter in to updateUserGroup method ****");
//		initializeDomainObject(userConfig, location);
//		getHibernateTemplate().update(location);
		update(userConfig, userGroup);
		logger.debug("**** Leaving from updateUserGroup method ****");
	}

	public UserGroup getUserGroupById(UserConfig userConfig,int recordId){
		logger.debug("**** Enter in to getUserGroupById method ****");
		UserGroup userGroup = null;
		userGroup = (UserGroup)getHibernateTemplate().get(UserGroup.class, Integer.valueOf(recordId));

		if(userGroup == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getUserGroupById method ****");
		return userGroup;
	}

	public UserGroup getUserGroupByCode(UserConfig userConfig,String code){
		logger.debug("**** Enter in to getUserGroupByCode method ****");
		UserGroup userGroup = null;
		Criteria criteria = getSession().createCriteria(UserGroup.class);
		criteria.add(Restrictions.eq("code", code));
		userGroup = (UserGroup)criteria.uniqueResult();

		if(userGroup == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getUserGroupByCode method ****");
		return userGroup;
	}

	public DataBag getAllUserGroup(UserConfig userConfig,List<QueryCriteria> criteriaList){
		logger.debug("**** Enter in to getAllUserGroup method ****");
		DataBag userGroupBag = null;
		Criteria criteria = getSession().createCriteria(UserGroup.class);
		userGroupBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllUserGroup method ****");
		return userGroupBag;
	}
}
