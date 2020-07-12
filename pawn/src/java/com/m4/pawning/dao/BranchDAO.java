package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;

public interface BranchDAO {
	void createBranch(UserConfig userConfig,Branch branch);
	void updateBranch(UserConfig userConfig,Branch branch);
	void removeBranch(UserConfig userConfig,Branch branch);
	
	Branch getBranchById(UserConfig userConfig,int recordId);
	Branch getBranchByCode(UserConfig userConfig,String code);
	DataBag getAllBranch(UserConfig userConfig,List<QueryCriteria> criteriaList);
	Branch getBranchByIdWithSystemDate(UserConfig userConfig, int recordId);
}
