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
import com.m4.pawning.dao.LocationDAO;
import com.m4.pawning.domain.Location;

public class LocationDAOImpl extends MasterDAOSupport implements LocationDAO {
	private static final Logger logger = Logger.getLogger(LocationDAOImpl.class);

	public void createLocation(UserConfig userConfig, Location location) {
		logger.debug("**** Enter in to createLocation method ****");
		Criteria criteria = getSession().createCriteria(Location.class);
		criteria.add(Restrictions.eq("code", location.getCode()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userConfig, location);
//        getHibernateTemplate().save(location);
        create(userConfig, location);
		logger.debug("**** Leaving from createLocation method ****");
	}

	public void removeLocation(UserConfig userConfig, Location location) {
		logger.debug("**** Enter in to removeLocation method ****");
//		Location status = (Location)getHibernateTemplate().get(Location.class, Integer.valueOf(location.getRecordId()));
//		status.setVersion(location.getVersion());
//		getHibernateTemplate().delete(status);
		remove(userConfig, location);
		logger.debug("**** Leaving from removeLocation method ****");
	}

	public void updateLocation(UserConfig userConfig, Location location) {
		logger.debug("**** Enter in to updateLocation method ****");
//		initializeDomainObject(userConfig, location);
//		getHibernateTemplate().update(location);
		update(userConfig, location);
		logger.debug("**** Leaving from updateLocation method ****");
	}

	public Location getLocationById(UserConfig userConfig,int recordId){
		logger.debug("**** Enter in to getLocationById method ****");
		Location location = null;
		location = (Location)getHibernateTemplate().get(Location.class, Integer.valueOf(recordId));

		if(location == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getLocationById method ****");
		return location;
	}

	public Location getLocationByCode(UserConfig userConfig,String code){
		logger.debug("**** Enter in to getLocationByCode method ****");
		Location location = null;
		Criteria criteria = getSession().createCriteria(Location.class);
		criteria.add(Restrictions.eq("code", code));
		location = (Location)criteria.uniqueResult();

		if(location == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getLocationByCode method ****");
		return location;
	}

	public DataBag getAllLocation(UserConfig userConfig,List<QueryCriteria> criteriaList){
		logger.debug("**** Enter in to getAllLocation method ****");
		DataBag locationBag = null;
		Criteria criteria = getSession().createCriteria(Location.class);
		locationBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllLocation method ****");
		return locationBag;
	}
}
