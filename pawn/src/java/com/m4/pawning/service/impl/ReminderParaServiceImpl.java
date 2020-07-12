package com.m4.pawning.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ReminderParaDAO;
import com.m4.pawning.domain.ReminderPara;
import com.m4.pawning.service.ReminderParaService;

public class ReminderParaServiceImpl extends AuthorizableServiceImpl implements	ReminderParaService {
	private ReminderParaDAO reminderParaDAO;
	
	public void setReminderParaDAO(ReminderParaDAO reminderParaDAO) {
		this.reminderParaDAO = reminderParaDAO;
	}

	public void createReminderPara(UserConfig userConfig,ReminderPara reminderPara) throws PawnException {
		reminderParaDAO.createReminderPara(userConfig, reminderPara);
	}

	public DataBag getAllReminderPara(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return reminderParaDAO.getAllReminderPara(userConfig, criteriaList);
	}

	public ReminderPara getReminderParaByCode(UserConfig userConfig, String code)throws PawnException {
		return reminderParaDAO.getReminderParaByCode(userConfig, code);
	}

	public ReminderPara getReminderParaById(UserConfig userConfig, int recordId)throws PawnException {
		return reminderParaDAO.getReminderParaById(userConfig, recordId);
	}

	public void removeReminderPara(UserConfig userConfig,ReminderPara reminderPara) throws PawnException {
		reminderParaDAO.removeReminderPara(userConfig, reminderPara);
	}

	public void updateReminderPara(UserConfig userConfig,ReminderPara reminderPara) throws PawnException {
		reminderParaDAO.updateReminderPara(userConfig, reminderPara);
	}
}
