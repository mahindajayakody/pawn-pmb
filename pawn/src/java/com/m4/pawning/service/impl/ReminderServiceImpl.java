package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ReminderDAO;
import com.m4.pawning.domain.Reminder;
import com.m4.pawning.service.ReminderService;

public class ReminderServiceImpl extends AuthorizableServiceImpl implements ReminderService {

	private ReminderDAO reminderDAO;
	public void setReminderDAO(ReminderDAO reminderDAO) {
		this.reminderDAO = reminderDAO;
	}
	public DataBag getAllReminder(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return reminderDAO.getAllReminder(userConfig, criteriaList);
	}

	public Reminder getReminderById(UserConfig userConfig, int recordId)throws PawnException {
		return reminderDAO.getReminderById(userConfig, recordId);
	}

	public void updateReminder(UserConfig userConfig, Reminder reminder)throws PawnException {
		reminderDAO.updateReminder(userConfig, reminder);
	}
	public Reminder getReminderByCode(UserConfig userConfig, String code)
			throws PawnException {
		return reminderDAO.getReminderByCode(userConfig, code);
	}
	

}
