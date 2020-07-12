package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.FundDeposit;
import com.m4.pawning.domain.FundRequest;

public interface FundDepositDAO {

	public void createFundDeposit(UserConfig userConfig,FundDeposit fundDeposit);
	public void approveFundDeposit(UserConfig userConfig,FundDeposit fundDeposit);
	
	public FundDeposit getFundDepositById(UserConfig userConfig,int recordId);
	public FundDeposit getFundDepositByDepositNo(UserConfig userConfig,String depositNo);
	public DataBag getAllFundDeposit(UserConfig userConfig,List<QueryCriteria> queryCriteria);
	public List<Object[]> getTiketsForTheDay(UserConfig userConfig) ;
	public List<Object[]> getReceiptsForTheDay(UserConfig userConfig) ;
	public List<Object[]> getTiketAllForTheDay(UserConfig userConfig) ;
	public List<Object[]> getReceiptAllForTheDay(UserConfig userConfig);
	public List<Object[]> getFundRequestAllForTheDay(UserConfig userConfig) ;
	public List<Object[]> getFundDepositAllForTheDay(UserConfig userConfig);
}
