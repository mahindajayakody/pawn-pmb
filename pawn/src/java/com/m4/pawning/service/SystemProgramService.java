package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.QueryCriteria;
import com.m4.pawning.domain.SystemProgram;


public interface SystemProgramService {
	public List<SystemProgram> getAllSystemPrograms(List<QueryCriteria> criteriaList)throws PawnException;
}
