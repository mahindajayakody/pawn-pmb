package com.m4.pawning.dao;


import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ReminderPara;

public interface ReminderParaDAO extends AuthorizableDAO {
	public void createReminderPara(UserConfig userConfig,ReminderPara reminderPara);
	public void updateReminderPara(UserConfig userConfig,ReminderPara reminderPara);
	public void removeReminderPara(UserConfig userConfig,ReminderPara reminderPara);
	
	public DataBag getAllReminderPara(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public ReminderPara getReminderParaById(UserConfig userConfig,int recordId);
	public ReminderPara getReminderParaByCode(UserConfig userConfig,String code);

}
