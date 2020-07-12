package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.dto.AuthorizeTicketDTO;

public interface TicketDAO {
	public String createTicket(UserConfig userConfig,Ticket ticket,List<TicketArticle> list);
	public String createTicket(UserConfig userConfig,Ticket ticket,List<TicketArticle> list,boolean redeem);
	public DataBag getAllTicket(UserConfig userConfig,List<QueryCriteria> criteriaList) ;
	public List getSerchTicketData(UserConfig userConfig,String sql);
	public Ticket getTicketById(UserConfig userConfig,int ticketId);
	public Ticket getTicketByTicketNumber(UserConfig userConfig,String number);
	public AuthorizeTicketDTO getAuthorizeTicketData(UserConfig userConfig,int ticketId);
	public void authorizeTicket(UserConfig userConfig, Ticket ticket, boolean authorize);
	public DataBag getAllTicketWithOR(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public List getClientExposure(UserConfig userConfig,int pawnId);
	public Ticket getTicketWithoutStatus(UserConfig userConfig, String code);
	public Ticket getBulkTicketById(UserConfig userConfig,int ticketId);

}
