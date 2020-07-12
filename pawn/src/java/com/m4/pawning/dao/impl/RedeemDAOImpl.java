package com.m4.pawning.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.BaseDAOSupport;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.RedeemDAO;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketRedeem;
import com.m4.pawning.util.RedeemType;
import com.m4.pawning.util.TicketStatusEnum;

public class RedeemDAOImpl extends BaseDAOSupport implements RedeemDAO {
	private static final Logger logger = Logger.getLogger(RedeemDAOImpl.class);
	
	public void redeem(UserConfig userConfig, int ticketId) {
		logger.debug("**** Enter in to redeem method ****");
		
		double outstanding = (Double)getSession().createCriteria(DueFrom.class)
							 .add(Restrictions.eq("ticketId", ticketId))
							 .setProjection(Projections.sum("balanceAmount"))
							 .uniqueResult();
		
		if(outstanding!=0)
			throw new CommonDataAccessException("errors.nonzerooutstanding");
		
		Ticket ticket = (Ticket)getSession().load(Ticket.class, Integer.valueOf(ticketId));
		ticket.setTicketStatusId(TicketStatusEnum.CLOSSED.getCode());
		ticket.setTicketClosedRenewalDate(userConfig.getLoginDate());
		initializeDomainObject(userConfig, ticket);
		getHibernateTemplate().update(ticket);
		
		TicketRedeem redeem = new TicketRedeem();
		redeem.setTicketId(ticketId);
		redeem.setProductId(ticket.getProductId());		
		redeem.setRedeemType(RedeemType.REDEEM.getCode());
		redeem.setRedeemUserId(userConfig.getUserId());
		redeem.setRedeemDate(userConfig.getLoginDate());
		redeem.setUserId(userConfig.getUserId());
		redeem.setLastUpdateDate((userConfig).getLoginDate());
		redeem.setCompanyId(userConfig.getCompanyId());
		redeem.setBranchId(userConfig.getBranchId());
		redeem.setApproveDate(userConfig.getLoginDate());
		redeem.setApproveUserId(userConfig.getUserId());
		
		getHibernateTemplate().save(redeem);
		
		logger.debug("**** Leaving from redeem method ****");
	}
	public void renew(UserConfig userConfig, int ticketId) {
		logger.debug("**** Enter in to redeem method ****");
		
//		double outstanding = (Double)getSession().createCriteria(DueFrom.class)
//							 .add(Restrictions.eq("ticketId", ticketId))
//							 .setProjection(Projections.sum("balanceAmount"))
//							 .uniqueResult();
//		
//		if(outstanding!=0)
//			throw new CommonDataAccessException("errors.nonzerooutstanding");
		
		Ticket ticket = (Ticket)getSession().load(Ticket.class, Integer.valueOf(ticketId));
		ticket.setTicketStatusId(TicketStatusEnum.CLOSSED.getCode());
		ticket.setTicketClosedRenewalDate(userConfig.getLoginDate());
		initializeDomainObject(userConfig, ticket);
		getHibernateTemplate().update(ticket);
		
		TicketRedeem renew = new TicketRedeem();
		renew.setTicketId(ticketId);
		renew.setProductId(ticket.getProductId());		
		renew.setRedeemType(RedeemType.RENEW.getCode());
		renew.setRedeemUserId(userConfig.getUserId());
		renew.setRedeemDate(userConfig.getLoginDate());
		renew.setUserId(userConfig.getUserId());
		renew.setLastUpdateDate((userConfig).getLoginDate());
		renew.setCompanyId(userConfig.getCompanyId());
		renew.setBranchId(userConfig.getBranchId());
		renew.setApproveDate(userConfig.getLoginDate());
		renew.setApproveUserId(userConfig.getUserId());
		
		getHibernateTemplate().save(renew);
		
		logger.debug("**** Leaving from redeem method ****");
	}

}
