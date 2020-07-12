package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.QueryCriteria;
import com.m4.pawning.domain.SystemProgram;


public interface SystemProgramDAO {
	public List<SystemProgram> getAllSystemPrograms(List<QueryCriteria> criteriaList);
}
