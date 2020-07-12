package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Company;

public interface CompanyService {
	public void createCompany(UserConfig userConfig,Company company)throws PawnException;
	public void updateCompany(UserConfig userConfig,Company company)throws PawnException;
	public void removeCompany(UserConfig userConfig,Company company)throws PawnException;
	
	public Company getCompanyById(UserConfig userConfig,int recordId)throws PawnException;
	public Company getCompanyByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllCompany(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
}
