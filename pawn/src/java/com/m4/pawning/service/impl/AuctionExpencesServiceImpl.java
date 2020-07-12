package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AuctionExpencesDAO;
import com.m4.pawning.domain.AuctionExpences;
import com.m4.pawning.service.AuctionExpencesService;

public class AuctionExpencesServiceImpl extends AuthorizableServiceImpl
		implements AuctionExpencesService {
	private AuctionExpencesDAO auctionExpencesDAO;

	public void setAuctionExpencesDAO(AuctionExpencesDAO auctionExpencesDAO) {
		this.auctionExpencesDAO = auctionExpencesDAO;
	}

	public void createAuctionExpences(UserConfig userConfig,
			AuctionExpences auctionExpences) throws PawnException {
		auctionExpencesDAO.createAuctionExpences(userConfig, auctionExpences);

	}

	public DataBag getAllAuctionExpences(UserConfig userConfig,
			List<QueryCriteria> queryCriteria) throws PawnException {		
		return auctionExpencesDAO.getAllAuctionExpences(userConfig, queryCriteria);
	}

	public AuctionExpences getAuctionExpencesByCode(UserConfig userConfig,
			String auctionCode) throws PawnException {
		return auctionExpencesDAO.getAuctionExpencesByCode(userConfig, auctionCode);
	}

	public AuctionExpences getAuctionExpencesById(UserConfig userConfig, int recordId)
			throws PawnException {
		return auctionExpencesDAO.getAuctionExpencesById(userConfig, recordId);
	}

	public void removeAuctionExpences(UserConfig userConfig,
			AuctionExpences auctionExpences) throws PawnException {
		auctionExpencesDAO.removeAuctionExpences(userConfig, auctionExpences);

	}

	public void updateAuctionExpences(UserConfig userConfig,
			AuctionExpences auctionExpences) throws PawnException {
		auctionExpencesDAO.updateAuctionExpences(userConfig, auctionExpences);

	}

	public AuctionExpences getAuctionExpencesByAuctionId(UserConfig userConfig,
			int auctionId) throws PawnException {
		return auctionExpencesDAO.getAuctionExpencesByAuctionId(userConfig, auctionId);
	}
	

}
