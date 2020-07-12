package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AuctionTicketDAO;
import com.m4.pawning.domain.AuctionMaster;
import com.m4.pawning.domain.AuctionTicket;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.DueType;

public class AuctionTicketDAOImpl extends MasterDAOSupport implements AuctionTicketDAO {
	private static final Logger logger=Logger.getLogger(AuctionTicketDAOImpl.class);
	

	public DataBag getAllAuctionTicket(UserConfig userConfig,
			List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllAuctionTicket method ****");
		DataBag auctionTicketBag=null;
		Criteria criteria=getSession().createCriteria(AuctionTicket.class);
		auctionTicketBag =getDataList(getHibernateTemplate(), criteriaList, criteria);
		
		logger.debug("**** Leaving getAllAuctionTicket method ****");
		return auctionTicketBag;
	}

	public AuctionTicket getAuctionTicketByAuctionId(UserConfig userConfig,
			int auctionId) {
		logger.debug("**** Enter in to getAuctionTicketByAuctionId method ****");
		AuctionTicket auctionTicket=null;
		Criteria criteria=getSession().createCriteria(AuctionTicket.class);
		criteria.add(Restrictions.eq("auctionId", auctionId));
		
		auctionTicket =(AuctionTicket)criteria.uniqueResult();
		
		if (auctionTicket==null)
			throw new CommonDataAccessException("error.recordnotfound");
		logger.debug("**** Leaving getAuctionTicketByAuctionId method ****");
		return auctionTicket;
	}

	public AuctionTicket getAuctionTicketById(UserConfig userConfig,
			int auctionTicketId) {
		logger.debug("**** Enter in to getAuctionTicketById method ****");
		AuctionTicket auctionTicket=null;
		auctionTicket =(AuctionTicket)getHibernateTemplate().get(AuctionTicket.class, auctionTicketId);
		
		if (auctionTicket==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getAuctionTicketById method ****");
		return auctionTicket;
	}

	public void allocate(UserConfig userConfig, Integer[] tickets,List<QueryCriteria>criteriaList,int auctionId) throws PawnException {
		logger.debug("**** Enter in to allocate method ****");
		List<AuctionMaster> auctionList=null;
		Criteria criteria= getSession().createCriteria(AuctionMaster.class);
		criteria.add(Restrictions.eq("auctionId", auctionId));
		auctionList=getDataList(getHibernateTemplate(), criteriaList, criteria).getDataList();
		
		Criteria dueTCriteria=getSession().createCriteria(DueType.class);
		dueTCriteria.add(Restrictions.eq("dueType", "AUE"));
		DueType dueType= (DueType) dueTCriteria.uniqueResult();
		
		if (dueType==null) {
			throw new CommonDataAccessException("errors.duetypenotfound");
		}
		
		for (int i = 0; i < tickets.length; i++) {
			AuctionTicket auctionTicket=null;
			
			Criteria auctionTicketCriteria=getSession().createCriteria(AuctionTicket.class);
			auctionTicketCriteria.add(Restrictions.eq("auctionId", auctionId));
			auctionTicketCriteria.add(Restrictions.eq("ticketId", tickets[i]));
			
			try {
				auctionTicket =(AuctionTicket)auctionTicketCriteria.uniqueResult();	
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
			
					
			for (AuctionMaster auctionMaster : auctionList) {
				double auctionExpences = auctionMaster.getAuctionExpences() * (auctionTicket.getMinimumCapital()/auctionMaster.getTotalCapital());
				auctionTicket.setAuctionExpences(auctionExpences);
				auctionTicket.setIsAllocated(1);
									
				DueFrom dueFrom = new DueFrom();
				dueFrom.setTicketId(tickets[i]);
				dueFrom.setDueAmount(auctionExpences);
				dueFrom.setDueTypeId(dueType.getDueTypeId());
				dueFrom.setPaidAmount(0.00);
				dueFrom.setBalanceAmount(auctionExpences);
				dueFrom.setBranchId(userConfig.getBranchId());
				dueFrom.setCompanyId(userConfig.getCompanyId());
				dueFrom.setProductId(1);
				
				getHibernateTemplate().update(auctionTicket);
				getHibernateTemplate().save(dueFrom);			
			}
		}		
		logger.debug("**** Enter in to allocate method ****");
	}

	public DataBag getAllAuctionMarkTicket(UserConfig userConfig,
			List<QueryCriteria> criteriList) {
		logger.debug("**** Enter in to getAllAuctionMarkTicket method ****");

		DataBag dataBagAuctionTicket = null;
		Criteria criteria = getSession().createCriteria(AuctionTicket.class);
		dataBagAuctionTicket = getDataList(getHibernateTemplate(), criteriList, criteria);
		logger.debug("**** Leaving getAllAuctionMarkTicket method ****");

		return dataBagAuctionTicket;
	}
	
	
}
