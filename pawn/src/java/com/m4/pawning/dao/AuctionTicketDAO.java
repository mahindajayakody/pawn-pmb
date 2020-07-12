package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.AuctionTicket;

public interface AuctionTicketDAO {
	public AuctionTicket getAuctionTicketByAuctionId(UserConfig userConfig, int auctionId) throws PawnException;
	public AuctionTicket getAuctionTicketById(UserConfig userConfig, int auctionTicketId) throws PawnException;
	public DataBag getAllAuctionTicket(UserConfig userConfig, List<QueryCriteria> criteriList) throws PawnException;
	public DataBag getAllAuctionMarkTicket(UserConfig userConfig, List<QueryCriteria> criteriList) throws PawnException;
	public void allocate(UserConfig userConfig,Integer[] tickets,List<QueryCriteria>criteriaList,int auctionId) throws PawnException;
}
