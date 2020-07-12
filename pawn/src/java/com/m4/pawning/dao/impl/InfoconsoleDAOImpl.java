package com.m4.pawning.dao.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.BaseDAOSupport;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.InfoconsoleDAO;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.Receipt;
import com.m4.pawning.domain.Schemes;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.dto.InfoConsoleDTO;
import com.m4.pawning.util.ReceiptStatusEnum;

public class InfoconsoleDAOImpl extends BaseDAOSupport implements InfoconsoleDAO {
	private static final Logger logger = Logger.getLogger(InfoconsoleDAOImpl.class);

	public InfoConsoleDTO getInfoConsoleData(UserConfig userConfig,int ticketId){
		logger.info("**** Enter in to getInfoConsoleData method ****");
		Ticket ticket = (Ticket)getSession().createCriteria(Ticket.class)
						.add(Restrictions.eq("ticketId",ticketId))
						.setFetchMode("pawner", FetchMode.JOIN)
						.setFetchMode("dueFromCollection", FetchMode.JOIN)
						.uniqueResult();

		if(ticket == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		InfoConsoleDTO consoleDTO = new InfoConsoleDTO();
		consoleDTO.setTicketId(ticketId);
		consoleDTO.setPawnerCode(ticket.getPawner().getPawnerCode());
		consoleDTO.setPawnerName(ticket.getPawner().getPawnerName());
		consoleDTO.setAddress(new StringBuilder().append(ticket.getPawner().getAddressLine1())
								  .append(" ").append(ticket.getPawner().getAddressLine2())
								  .append(" ").append(ticket.getPawner().getAddressLine3())
								  .append(" ").append(ticket.getPawner().getAddressLine4()).toString());
		consoleDTO.setPawnAdvance(ticket.getPawnAdvance());
		consoleDTO.setMarketValue(ticket.getTotalMarketValue());
		consoleDTO.setActualDisValue(ticket.getTotalAssedValue());
		consoleDTO.setTotalNetWeight(ticket.getTotalNetWeight());
		consoleDTO.setTicketDate(ticket.getTicketDate());
		//consoleDTO.setAuthorizeDate();
		consoleDTO.setExpiraryDate(ticket.getTicketExpiryDate());
		//consoleDTO.setPrintedDate();
		Schemes schemes = (Schemes)getSession().get(Schemes.class,ticket.getSchemeId());
		consoleDTO.setSchemeCode(schemes.getCode());
		consoleDTO.setInterestCode(schemes.getInterestCode());
		consoleDTO.setSchemeDescription(schemes.getDescription());
		consoleDTO.setInterestId(ticket.getInterestSlabId());
		consoleDTO.setTicketStatusId(ticket.getTicketStatusId());
		consoleDTO.setOfficerName(ticket.getOfficer() == null ? "":ticket.getOfficer().getUserName());
		consoleDTO.setTicketCloseDate(ticket.getTicketClosedRenewalDate());

		consoleDTO.setTicketArticleList(getSession().createCriteria(TicketArticle.class)
										.add(Restrictions.eq("ticketId",ticket.getTicketId()))
										.list());
		consoleDTO.setDueFromList((List)ticket.getDueFromCollection());

		Object result = getSession().createCriteria(Receipt.class)
		 				.add(Restrictions.eq("ticketId", ticketId))
		 				.add(Restrictions.eq("status", ReceiptStatusEnum.ACTIVE.getCode()))
		 				.setProjection(Projections.sum("receiptAmt"))
		 				.uniqueResult();

		if(result==null)
			result = 0.0;

		consoleDTO.setTotalReceiptAmount((Double)result);
		consoleDTO.setIsAuctioned(ticket.getIsAuctioned());

		if(ticket.getApprovedBy() != 0)
			consoleDTO.setApproveUserName(((Officer)getSession().get(Officer.class, ticket.getApprovedBy())).getUserName());

		logger.info("**** Leaving from getInfoConsoleData method ****");
		return consoleDTO;
	}
}
