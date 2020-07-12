package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.FundDeposit;
import com.m4.pawning.domain.FundRequest;

public interface FundDepositService extends AuthorizableService {
	public void createFundDeposit(UserConfig userConfig,FundDeposit fundDeposit) throws PawnException;
	public void approveFundDeposit(UserConfig userConfig,FundDeposit fundDeposit) throws PawnException;
	
	public FundDeposit getFundDepositById(UserConfig userConfig,int recordId) throws PawnException;
	public FundDeposit getFundDepositByDepositNo(UserConfig userConfig,String depositNo) throws PawnException;
	public DataBag getAllFundDeposit(UserConfig userConfig, List<QueryCriteria>criteriaList) throws PawnException;
	public List<Object[]> getTiketsForTheDay(UserConfig userConfig) throws PawnException;
	public List<Object[]> getReceiptsForTheDay(UserConfig userConfig) throws PawnException;
	public List<Object[]> getTiketAllForTheDay(UserConfig userConfig)throws PawnException;
	public List<Object[]> getReceiptAllForTheDay(UserConfig userConfig)throws PawnException;
	public List<Object[]> getFundRequestAllForTheDay(UserConfig userConfig) throws PawnException;
	public List<Object[]> getFundDepositAllForTheDay(UserConfig userConfig)throws PawnException;
}
