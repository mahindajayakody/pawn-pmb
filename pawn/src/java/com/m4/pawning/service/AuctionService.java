package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.AuctionMaster;

public interface AuctionService extends AuthorizableService {
	public void createAuction(UserConfig userConfig,AuctionMaster auctionMaster) throws PawnException;
	public void updateAuction(UserConfig userConfig,AuctionMaster auctionMaster) throws PawnException;
	public void removeAuction(UserConfig userConfig,AuctionMaster auctionMaster) throws PawnException;
	public AuctionMaster getAuctionById(UserConfig userConfig,int recordId)throws PawnException;
	public AuctionMaster getAuctionByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllAuction(UserConfig userConfig,List<QueryCriteria>criteriaList) throws PawnException;
	public DataBag getAllActiveAuction(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
	public List getAllLapsTickets(UserConfig userConfig,String sql)throws PawnException;
	public void createAuctionMark(UserConfig userConfig, int branchId,int productId,int auctionId ,Integer[] tikets, Double upsetValue) throws PawnException;
	public List<AuctionMaster> getAllCommonAuction(UserConfig userSession, int isCommon);
}
