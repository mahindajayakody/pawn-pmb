package com.m4.pawning.service;

import java.util.List;
import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.InterestRates;
import com.m4.pawning.domain.InterestSlab;
public interface InterestRatesService extends AuthorizableService{
	public void createInterestRates(UserConfig userConfig, InterestRates interestRates,List<InterestSlab> slabs)throws PawnException;
	public void updateInterestRates(UserConfig userConfig,InterestRates interestRates,List<InterestSlab> slabs)throws PawnException;

	public InterestRates getInterestRateById(UserConfig userConfig,int recordId)throws PawnException;
	public InterestRates getInterestRateByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllInterestRates(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
	public DataBag getAllInterestSlabs(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException;
}
