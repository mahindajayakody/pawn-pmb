package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.FundRequest;

public interface FundRequestService extends AuthorizableService {
	public void createFundRequest(UserConfig userConfig,FundRequest fundRequest) throws PawnException;
	public void approveFundRequest(UserConfig userConfig,FundRequest fundRequest) throws PawnException;
	public void updateFundRequest(UserConfig userConfig,FundRequest fundRequest) throws PawnException;
	
	public FundRequest getFundRequestById(UserConfig userConfig,int recordId) throws PawnException;
	public FundRequest getFundRequestByRequestNo(UserConfig userConfig,String requestNo) throws PawnException;
	public DataBag getAllFundRequest(UserConfig userConfig, List<QueryCriteria>criteriaList) throws PawnException;
	
}
