package com.m4.pawning.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.m4.core.exception.PawnException;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.DayEndDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.service.DayEndService;

public class DayEndServiceImpl implements DayEndService {
	private static final Logger logger = Logger.getLogger(DayEndServiceImpl.class);
	private DayEndDAO dayEndDAO;
	Calendar calendar=Calendar.getInstance();
	
	public void setDayEndDAO(DayEndDAO dayEndDAO) {
		this.dayEndDAO = dayEndDAO;
	}
	
	public void doDayEndProcess(UserConfig userConfig, Integer[] branchs)throws PawnException {
		logger.debug("**** Enter in to doDayEndProcess method ****" + calendar.getTime());
		dayEndDAO.doDayEndProcess(userConfig, branchs);
		logger.debug("**** Enter in to doDayEndProcess method ****" + calendar.getTime());
	}

	public List<Branch> getInitialData(UserConfig userConfig)throws PawnException {
		return dayEndDAO.getInitialData(userConfig);
	}

	public void processPasswordExpire(UserConfig userConfig)
			throws PawnException {
		logger.debug("**** Enter in to processPasswordExpire method ****" + calendar.getTime());
		dayEndDAO.processPasswordExpire(userConfig);
		logger.debug("**** Enter in to processPasswordExpire method ****" + calendar.getTime());
	}

}
