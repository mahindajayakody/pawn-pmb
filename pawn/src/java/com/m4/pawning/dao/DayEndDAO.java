package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;

public interface DayEndDAO {
	public void doDayEndProcess(UserConfig userConfig,Integer[] branchs);
	public List<Branch> getInitialData(UserConfig userConfig);
	public void processPasswordExpire(UserConfig userConfig);
	
}
