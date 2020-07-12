package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Reminder;

public interface ReminderDAO extends AuthorizableDAO {

	public DataBag getAllReminder(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public Reminder getReminderById(UserConfig userConfig,int recordId);
	public Reminder getReminderByCode(UserConfig userConfig,String code);
	public void updateReminder(UserConfig userConfig,Reminder reminder);
}
