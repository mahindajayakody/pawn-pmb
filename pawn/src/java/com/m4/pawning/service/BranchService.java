package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Location;

public interface BranchService{
	void createBranch(UserConfig userConfig,Branch branch)throws PawnException;
	void updateBranch(UserConfig userConfig,Branch branch)throws PawnException;
	void removeBranch(UserConfig userConfig,Branch branch)throws PawnException;

	Branch getBranchById(UserConfig userConfig,int recordId)throws PawnException;
	Branch getBranchByCode(UserConfig userConfig,String code)throws PawnException;
	DataBag getAllBranch(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
	Branch getBranchByIdWithSystemDate(UserConfig userConfig, int recordId)throws PawnException;
}
