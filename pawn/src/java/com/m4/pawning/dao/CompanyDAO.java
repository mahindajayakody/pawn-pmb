package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Company;

public interface CompanyDAO {
	public void createCompany(UserConfig userConfig,Company company);
	public void updateCompany(UserConfig userConfig,Company company);
	public void removeCompany(UserConfig userConfig,Company company);
	
	public Company getCompanyById(UserConfig userConfig,int recordId);
	public Company getCompanyByCode(UserConfig userConfig,String code);
	public DataBag getAllCompany(UserConfig userConfig,List<QueryCriteria> criteriaList);

}
