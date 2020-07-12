package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.AuctionLocation;

public interface AuctionLocationDAO extends AuthorizableDAO{
	public void createAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation);
	public void updateAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation);
	public void removeAuctionLocation(UserConfig userConfig,AuctionLocation auctionLocation);

	public AuctionLocation getAuctionLocationById(UserConfig userConfig,int recordId);
	public AuctionLocation getAuctionLocationByCode(UserConfig userConfig,String code);
	public DataBag getAllAuctionLocation(UserConfig userConfig,List<QueryCriteria> criteriaList);

}
