package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Reminder;

public interface ReminderService extends AuthorizableService {
	public DataBag getAllReminder(UserConfig userConfig,List<QueryCriteria>criteriaList ) throws PawnException;
	public Reminder getReminderById(UserConfig userConfig,int recordId) throws PawnException;
	public Reminder getReminderByCode(UserConfig userConfig,String code) throws PawnException;
	public void updateReminder(UserConfig userConfig,Reminder reminder)throws PawnException;
}
