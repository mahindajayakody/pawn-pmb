package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;

public interface DayEndService {
	public void doDayEndProcess(UserConfig userConfig,Integer[] branchs)throws PawnException;
	public void processPasswordExpire(UserConfig userConfig)throws PawnException;
	public List<Branch> getInitialData(UserConfig userConfig)throws PawnException;
}
