package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AuctionExpencesDAO;
import com.m4.pawning.domain.AuctionExpences;
import com.m4.pawning.domain.AuctionMaster;

public class AuctionExpencesDAOImpl extends MasterDAOSupport implements
		AuctionExpencesDAO {
	private static final Logger logger = Logger.getLogger(AuctionExpencesDAOImpl.class);
	
	

	public void createAuctionExpences(UserConfig userConfig,
			AuctionExpences auctionExpences) {
		logger.debug("**** Enter in to createAuctionExpences Method ****");
		Criteria criteria = getSession().createCriteria(AuctionExpences.class);
		criteria.add(Restrictions.eq("companyId", auctionExpences.getCompanyId()));
		criteria.add(Restrictions.eq("branchId", auctionExpences.getBranchId()));
		criteria.add(Restrictions.eq("auctionId", auctionExpences.getAuctionId()));
		criteria.add(Restrictions.eq("duetypeId", auctionExpences.getDuetypeId()));
		criteria.setProjection(Projections.rowCount());
		AuctionMaster auctionMaster = new AuctionMaster();
		
		int count = ((Integer) criteria.uniqueResult()).intValue();
		if (count != 0)
			throw new CommonDataAccessException("errors.recordexist");
		create(userConfig, auctionExpences);	
		auctionMaster=(AuctionMaster)getHibernateTemplate().get(AuctionMaster.class, auctionExpences.getAuctionId());
		auctionMaster.setAuctionExpences(auctionMaster.getAuctionExpences() + auctionExpences.getAmount());
		//auctionMaster.setAuctionId(auctionExpences.getAuctionId());
		update(userConfig, auctionMaster);
		logger.debug("**** Leaving createAuctionExpences Method ****");
	}

	public DataBag getAllAuctionExpences(UserConfig userConfig,
			List<QueryCriteria> queryCriteria) {
		DataBag auctionExpencesBag=null;
		Criteria criteria=getSession().createCriteria(AuctionExpences.class);
		auctionExpencesBag=getDataList(getHibernateTemplate(), queryCriteria, criteria);
		
		return auctionExpencesBag;
	}

	public AuctionExpences getAuctionExpencesByCode(UserConfig userConfig,
			String auctionCode) {
		logger.debug("**** Enter in to getAuctionExpencesByCode method ****");
		AuctionExpences auctionExpences=null;
//		Criteria criteria = getSession().createCriteria(AuctionExpences.class);
//		criteria.add(Restrictions.eq(", value))
//		auctionExpences=(AuctionExpences)getHibernateTemplate().get(AuctionExpences.class, auctionCode);
//		
//		if (auctionExpences==null)
//			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getAuctionExpencesByCode method ****");
		return auctionExpences;
	}

	public AuctionExpences getAuctionExpencesById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getAuctionExpencesById method ****");
		AuctionExpences auctionExpences=null;
		auctionExpences=(AuctionExpences)getHibernateTemplate().get(AuctionExpences.class, recordId);
		
		if (auctionExpences==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getAuctionExpencesById method ****");
		return auctionExpences;
	}

	public void removeAuctionExpences(UserConfig userConfig,
			AuctionExpences auctionExpences) {
		logger.debug("**** Enter in to removeAuctionExpences method ****");
		AuctionMaster auctionMaster = new AuctionMaster();
		remove(userConfig, auctionExpences);
		auctionMaster=(AuctionMaster)getHibernateTemplate().get(AuctionMaster.class, auctionExpences.getAuctionId());
		auctionMaster.setAuctionExpences(auctionMaster.getAuctionExpences()-auctionExpences.getAmount());
		auctionMaster.setAuctionId(auctionExpences.getAuctionId());
		update(userConfig, auctionMaster);
		logger.debug("**** Leaving removeAuctionExpences method ****");
	}

	public void updateAuctionExpences(UserConfig userConfig,
			AuctionExpences auctionExpences) {
		logger.debug("**** Enter in to updateAuctionExpences method ****");
		AuctionMaster auctionMaster = new AuctionMaster();
		update(userConfig, auctionExpences);
		auctionMaster=(AuctionMaster)getHibernateTemplate().get(AuctionMaster.class, auctionExpences.getAuctionId());
		auctionMaster.setAuctionExpences(auctionMaster.getAuctionExpences()+auctionExpences.getAmount());
		auctionMaster.setAuctionId(auctionExpences.getAuctionId());
		update(userConfig, auctionMaster);
		logger.debug("**** Leaving updateAuctionExpences method ****");
	}

	public AuctionExpences getAuctionExpencesByAuctionId(UserConfig userConfig,
			int auctionId) {
		logger.debug("**** Enter in to getAuctionExpencesById method ****");
		AuctionExpences auctionExpences=null;
		Criteria criteria =getSession().createCriteria(AuctionExpences.class);
		criteria.add(Restrictions.eq("auctionId", auctionId));
		auctionExpences=(AuctionExpences) criteria.uniqueResult();
		
		if (auctionExpences==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getAuctionExpencesById method ****");
		return auctionExpences;
	}
}
