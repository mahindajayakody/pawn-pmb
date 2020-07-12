package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.dto.AuthorizeTicketDTO;

public interface TicketService{
	public String createTicket(UserConfig userConfig,Ticket ticket,List<TicketArticle> list)throws PawnException;
	public String createTicket(UserConfig userConfig,Ticket ticket,List<TicketArticle> list,boolean redeem)throws PawnException;
	public DataBag getAllTicket(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException;
	public List getSerchTicketData(UserConfig userConfig,String sql)throws PawnException;
	public Ticket getTicketById(UserConfig userConfig,int ticketId)throws PawnException;
	public Ticket getTicketByTicketNumber(UserConfig userConfig,String number)throws PawnException;
	public AuthorizeTicketDTO getAuthorizeTicketData(UserConfig userConfig,int ticketId)throws PawnException;
	public void authorizeTicket(UserConfig userConfig, Ticket ticket,boolean authorize)throws PawnException;
	public DataBag getAllTicketWithOR(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
	public List getClientExposure(UserConfig userConfig,int pawnerId) throws PawnException;
	public Ticket getTicketWithoutStatus(UserConfig userSession, String code)throws PawnException;
	public Ticket getBulkTicketById(UserConfig userConfig,int ticketId)throws PawnException;

}
