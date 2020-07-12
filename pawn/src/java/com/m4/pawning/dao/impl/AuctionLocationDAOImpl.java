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
import com.m4.pawning.dao.AuctionLocationDAO;
import com.m4.pawning.domain.AuctionLocation;

public class AuctionLocationDAOImpl extends MasterDAOSupport implements AuctionLocationDAO {
	private static final Logger logger=Logger.getLogger(AuctionLocationDAOImpl.class);

	public void createAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation) {
		logger.debug("**** Enter in to createAuctionLocation Method ****");
		Criteria criteria=getSession().createCriteria(AuctionLocation.class);
		criteria.add(Restrictions.eq("code", auctionLocation.getCode()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userConfig, auctionLocation);
//        getHibernateTemplate().save(auctionLocation);
        create(userConfig, auctionLocation);
		logger.debug("**** Leaving createAuctionLocation Method ****");
	}

	public void removeAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation) {
		logger.debug("**** Enter in to removeAuctionLocation Method ****");
//		AuctionLocation auctionLoc=(AuctionLocation) getHibernateTemplate().get(AuctionLocation.class, Integer.valueOf(auctionLocation.getRecordId()));
//		auctionLoc.setVersion(auctionLoc.getVersion());
//		getHibernateTemplate().delete(auctionLoc);
		remove(userConfig, auctionLocation);
		logger.debug("**** Leaving removeAuctionLocation Method ****");
	}

	public void updateAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation) {
		logger.debug("**** Enter in to updateAuctionLocation Method ****");
//		initializeDomainObject(userConfig, auctionLocation);
//		getHibernateTemplate().update(auctionLocation);
		update(userConfig, auctionLocation);
		logger.debug("**** Leaving updateAuctionLocation Method ****");
	}

	public DataBag getAllAuctionLocation(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllAuctionLocation Method ****");
		DataBag auctionLocationBag = null;
		Criteria criteria  = getSession().createCriteria(AuctionLocation.class);
		auctionLocationBag = getDataList(getHibernateTemplate(),criteriaList,criteria);
		logger.debug("**** Leaving getAllAuctionLocation Method ****");
		return auctionLocationBag;
	}

	public AuctionLocation getAuctionLocationByCode(UserConfig userConfig,String code) {
		logger.debug("**** Enter in to getAuctionLocationByCode method ****");
		AuctionLocation auctionLocation=null;
		Criteria criteria = getSession().createCriteria(AuctionLocation.class);
		criteria.add(Restrictions.eq("code", auctionLocation.getCode()));
		auctionLocation = (AuctionLocation) criteria.uniqueResult();

		if (auctionLocation == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getAuctionLocationByCode method ****");

		return auctionLocation;
	}

	public AuctionLocation getAuctionLocationById(UserConfig userConfig,int recordId) {
		logger.debug("**** Enter in to getAuctionLocationById method ****");
		AuctionLocation auctionLocation=null;
		auctionLocation=(AuctionLocation)getHibernateTemplate().get(AuctionLocation.class, Integer.valueOf(recordId));

		if (auctionLocation == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getAuctionLocationById method ****");

		return auctionLocation;
	}
}
