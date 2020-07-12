package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.TicketStatus;

public interface TicketStatusService extends AuthorizableService{
	public void createTicketStatus(UserConfig userConfig,TicketStatus ticketStatus)throws PawnException;
	public void updateTicketStatus(UserConfig userConfig,TicketStatus ticketStatus)throws PawnException;
	public void removeTicketStatus(UserConfig userConfig,TicketStatus ticketStatus)throws PawnException;

	public TicketStatus geTicketStatusById(UserConfig userConfig,int recordId)throws PawnException;
	public TicketStatus geTicketStatusByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllTicketStatus(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
}
