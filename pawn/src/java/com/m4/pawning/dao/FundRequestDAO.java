package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.FundRequest;

public interface FundRequestDAO {

	public void createFundRequest(UserConfig userConfig,FundRequest fundRequest);
	public void updateFundRequest(UserConfig userConfig,FundRequest fundRequest);
	public void approveFundRequest(UserConfig userConfig,FundRequest fundRequest);
	
	public FundRequest getFundRequestById(UserConfig userConfig,int recordId);
	public FundRequest getFundRequestByRequestNo(UserConfig userConfig,String requestNo);
	public DataBag getAllFundRequest(UserConfig userConfig,List<QueryCriteria> queryCriteria);
}
