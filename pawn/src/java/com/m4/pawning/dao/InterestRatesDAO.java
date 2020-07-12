package com.m4.pawning.dao;


import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.InterestRates;
import com.m4.pawning.domain.InterestSlab;


public interface InterestRatesDAO extends AuthorizableDAO{
	public void createInterestRates(UserConfig userConfig, InterestRates interestRates,List<InterestSlab> slabs);
	public void updateInterestRates(UserConfig userConfig,InterestRates interestRates,List<InterestSlab> slabs);

	public InterestRates getInterestRateById(UserConfig userConfig,int recordId);
	public InterestRates getInterestRateByCode(UserConfig userConfig,String code);
	public DataBag getAllInterestRates(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public DataBag getAllInterestSlabs(UserConfig userConfig,List<QueryCriteria> criteriaList) ;

}
