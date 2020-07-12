package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AuctionTicketDAO;
import com.m4.pawning.dao.DueFromDAO;
import com.m4.pawning.dao.DueTypeDAO;
import com.m4.pawning.domain.AuctionTicket;
import com.m4.pawning.service.AuctionTicketService;

public class AuctionTicketServiceImpl implements AuctionTicketService {
	private AuctionTicketDAO auctionTicketDAO;
	private DueFromDAO dueFromDAO;
	private DueTypeDAO dueTypeDAO;
	
	
	/**
	 * @param dueTypeDAO the dueTypeDAO to set
	 */
	public void setDueTypeDAO(DueTypeDAO dueTypeDAO) {
		this.dueTypeDAO = dueTypeDAO;
	}

	/**
	 * @param dueFromDAO the dueFromDAO to set
	 */
	public void setDueFromDAO(DueFromDAO dueFromDAO) {
		this.dueFromDAO = dueFromDAO;
	}

	public void setAuctionTicketDAO(AuctionTicketDAO auctionTicketDAO) {
		this.auctionTicketDAO = auctionTicketDAO;
	}

	public DataBag getAllAuctionTicket(UserConfig userConfig,
			List<QueryCriteria> criteriList) throws PawnException {
		return auctionTicketDAO.getAllAuctionTicket(userConfig, criteriList);
	}


	public AuctionTicket getAuctionTicketByAuctionId(UserConfig userConfig,
			int auctionId) throws PawnException {
		return auctionTicketDAO.getAuctionTicketByAuctionId(userConfig, auctionId);
	}


	public AuctionTicket getAuctionTicketById(UserConfig userConfig,
			int auctionTicketId) throws PawnException {
		return auctionTicketDAO.getAuctionTicketById(userConfig, auctionTicketId);
	}

	public void allocate(UserConfig userConfig, Integer[] tickets,List<QueryCriteria>criteriaList,int auctionId)
			throws PawnException {
		auctionTicketDAO.allocate(userConfig, tickets,criteriaList,auctionId);		
	}

	public DataBag getAllAuctionMarkTicket(UserConfig userConfig,
			List<QueryCriteria> criteriList) throws PawnException {
		return auctionTicketDAO.getAllAuctionMarkTicket(userConfig, criteriList);
	}
	

}
