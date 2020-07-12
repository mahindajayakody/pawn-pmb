package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.DueFrom;

public interface DueFromDAO extends AuthorizableDAO{
	public void createDueFrom(UserConfig userConfig,DueFrom dueFrom);

	public DueFrom getDueFromById(UserConfig userConfig,int recordId);
	public DataBag getAllDueFrom(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public DataBag getAllDueFromByTicket(UserConfig userConfig,int ticketId);

}
