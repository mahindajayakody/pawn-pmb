package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.AuctionExpences;


public interface AuctionExpencesDAO extends AuthorizableDAO {
	public void createAuctionExpences(UserConfig userConfig,AuctionExpences auctionExpences);
	public void updateAuctionExpences(UserConfig userConfig,AuctionExpences auctionExpences);
	public void removeAuctionExpences(UserConfig userConfig,AuctionExpences auctionExpences);

	public DataBag getAllAuctionExpences(UserConfig userConfig,List<QueryCriteria> queryCriteria);
	public AuctionExpences getAuctionExpencesById(UserConfig userConfig,int recordId);
	public AuctionExpences getAuctionExpencesByCode(UserConfig userConfig,String auctionCode);
	public AuctionExpences getAuctionExpencesByAuctionId(UserConfig userConfig,int auctionId);

}
