package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.TicketStatus;

public interface TicketStatusDAO extends AuthorizableDAO{
	public void createTicketStatus(UserConfig userConfig,TicketStatus ticketStatus);
	public void updateTicketStatus(UserConfig userConfig,TicketStatus ticketStatus);
	public void removeTicketStatus(UserConfig userConfig,TicketStatus ticketStatus);

	public TicketStatus geTicketStatusById(UserConfig userConfig,int recordId);
	public TicketStatus geTicketStatusByCode(UserConfig userConfig,String code);
	public DataBag getAllTicketStatus(UserConfig userConfig,List<QueryCriteria> criteriaList);
}
