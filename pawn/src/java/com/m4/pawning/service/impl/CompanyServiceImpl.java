package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.CompanyDAO;
import com.m4.pawning.domain.Company;
import com.m4.pawning.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {
	private CompanyDAO companyDAO;
	
	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}	

	public void createCompany(UserConfig userConfig, Company company) throws PawnException {
		companyDAO.createCompany(userConfig, company);
	}
	
	public void removeCompany(UserConfig userConfig, Company company) throws PawnException {
		companyDAO.removeCompany(userConfig, company);
	}

	public void updateCompany(UserConfig userConfig, Company company) throws PawnException {
		companyDAO.updateCompany(userConfig, company);
	}

	public DataBag getAllCompany(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException {
		return companyDAO.getAllCompany(userConfig, criteriaList);
	}

	public Company getCompanyByCode(UserConfig userConfig, String code) throws PawnException {
		return companyDAO.getCompanyByCode(userConfig, code);
	}

	public Company getCompanyById(UserConfig userConfig, int recordId) throws PawnException {
		return companyDAO.getCompanyById(userConfig, recordId);
	}
}
