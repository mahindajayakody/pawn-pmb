package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;

import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.pawning.dao.SystemProgramDAO;
import com.m4.pawning.domain.SystemProgram;


public class SystemProgramDAOImpl extends MasterDAOSupport implements SystemProgramDAO {
	Logger logger = Logger.getLogger(SystemProgramDAOImpl.class);
	
	public List<SystemProgram> getAllSystemPrograms(List<QueryCriteria> criteriaList){
		logger.info("*** Enter in to the getAllSystemPrograms method ***");
		List<SystemProgram> systemProgramList = null;
		Criteria criteria = getSession().createCriteria(SystemProgram.class);
		systemProgramList = getDataList(getHibernateTemplate(), criteriaList, criteria).getDataList();
		logger.info("*** Leaving from the getAllSystemPrograms method ***");
		return systemProgramList;
	}
}
