package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.AuctionLocation;


public interface AuctionLocationService extends AuthorizableService{
	public void createAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation)throws PawnException;
	public void updateAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation)throws PawnException;
	public void removeAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation)throws PawnException;

	public AuctionLocation getAuctionLocationById(UserConfig userConfig,int recordId)throws PawnException;
	public AuctionLocation getAuctionLocationByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllAuctionLocation(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;

}
