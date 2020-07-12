package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.BranchDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.service.BranchService;

public class BranchServiceImpl implements BranchService {
	private BranchDAO branchDAO;
	
	public void setBranchDAO(BranchDAO branchDAO) {
		this.branchDAO = branchDAO;
	}

	public void createBranch(UserConfig userConfig, Branch branch) throws PawnException {
		branchDAO.createBranch(userConfig, branch);
	}

	public void removeBranch(UserConfig userConfig, Branch branch) throws PawnException {
		branchDAO.removeBranch(userConfig, branch);
	}

	public void updateBranch(UserConfig userConfig, Branch branch) throws PawnException {
		branchDAO.updateBranch(userConfig, branch);
	}
	
	public DataBag getAllBranch(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException {
		return branchDAO.getAllBranch(userConfig, criteriaList);
	}

	public Branch getBranchByCode(UserConfig userConfig, String code) throws PawnException {
		return branchDAO.getBranchByCode(userConfig, code);
	}

	public Branch getBranchById(UserConfig userConfig, int recordId) throws PawnException {
		return branchDAO.getBranchById(userConfig, recordId);
	}

	public Branch getBranchByIdWithSystemDate(UserConfig userConfig, int recordId) throws PawnException {
		return branchDAO.getBranchByIdWithSystemDate(userConfig, recordId);
	}
}
