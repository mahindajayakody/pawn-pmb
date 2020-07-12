package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.TicketStatusDAO;
import com.m4.pawning.domain.TicketStatus;
import com.m4.pawning.service.TicketStatusService;

public class TicketStatusServiceImpl extends AuthorizableServiceImpl implements TicketStatusService {
	private TicketStatusDAO ticketStatusDAO;

	public void setTicketStatusDAO(TicketStatusDAO ticketStatusDAO) {
		this.ticketStatusDAO = ticketStatusDAO;
	}

	public void createTicketStatus(UserConfig userConfig, TicketStatus ticketStatus) throws PawnException {
		ticketStatusDAO.createTicketStatus(userConfig, ticketStatus);
	}

	public void removeTicketStatus(UserConfig userConfig,TicketStatus ticketStatus) throws PawnException {
		ticketStatusDAO.removeTicketStatus(userConfig, ticketStatus);
	}

	public void updateTicketStatus(UserConfig userConfig,TicketStatus ticketStatus) throws PawnException {
		ticketStatusDAO.updateTicketStatus(userConfig, ticketStatus);
	}

	public TicketStatus geTicketStatusByCode(UserConfig userConfig, String code) throws PawnException {
		return ticketStatusDAO.geTicketStatusByCode(userConfig, code);
	}

	public TicketStatus geTicketStatusById(UserConfig userConfig, int recordId) throws PawnException {
		return ticketStatusDAO.geTicketStatusById(userConfig, recordId);
	}

	public DataBag getAllTicketStatus(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException {
		return ticketStatusDAO.getAllTicketStatus(userConfig, criteriaList);
	}

}
