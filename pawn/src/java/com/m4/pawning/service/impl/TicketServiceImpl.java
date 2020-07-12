package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.TicketDAO;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.dto.AuthorizeTicketDTO;
import com.m4.pawning.service.TicketService;

public class TicketServiceImpl implements TicketService {
	public Ticket getTicketWithoutStatus(UserConfig userConfig, String code)
			throws PawnException {
		return ticketDAO.getTicketWithoutStatus(userConfig, code);
	}

	private TicketDAO ticketDAO;
	
	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}

	public String createTicket(UserConfig userConfig, Ticket ticket, List<TicketArticle> list) {
		return ticketDAO.createTicket(userConfig, ticket, list);
	}
	public String createTicket(UserConfig userConfig, Ticket ticket, List<TicketArticle> list,boolean redeem) {
		return ticketDAO.createTicket(userConfig, ticket, list,redeem);
	}

	public DataBag getAllTicket(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException {
		return ticketDAO.getAllTicket(userConfig, criteriaList);
	}

	public List getSerchTicketData(UserConfig userConfig,String sql) throws PawnException {
		return ticketDAO.getSerchTicketData(userConfig,sql);
	}

	public Ticket getTicketById(UserConfig userConfig, int ticketId) throws PawnException {
		return ticketDAO.getTicketById(userConfig, ticketId);
	}

	public Ticket getTicketByTicketNumber(UserConfig userConfig, String number) throws PawnException {
		return ticketDAO.getTicketByTicketNumber(userConfig, number);
	}

	public void authorizeTicket(UserConfig userConfig, Ticket ticket , boolean authorize) throws PawnException {
		ticketDAO.authorizeTicket(userConfig, ticket , authorize);
	}

	public AuthorizeTicketDTO getAuthorizeTicketData(UserConfig userConfig, int ticketId) throws PawnException {
		return ticketDAO.getAuthorizeTicketData(userConfig, ticketId);
	}

	public DataBag getAllTicketWithOR(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException {
		return ticketDAO.getAllTicketWithOR(userConfig, criteriaList);
	}

	public List getClientExposure(UserConfig userConfig, int pawnId) throws PawnException {
		return ticketDAO.getClientExposure(userConfig, pawnId);
	}
	public Ticket getBulkTicketById(UserConfig userConfig, int ticketId) throws PawnException {
		return ticketDAO.getBulkTicketById(userConfig, ticketId);
	}
}
