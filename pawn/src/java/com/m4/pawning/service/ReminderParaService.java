package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ReminderPara;

public interface ReminderParaService extends AuthorizableService {
	public void createReminderPara(UserConfig userConfig,ReminderPara reminderPara) throws PawnException;
	public void updateReminderPara(UserConfig userConfig,ReminderPara reminderPara) throws PawnException;
	public void removeReminderPara(UserConfig userConfig,ReminderPara reminderPara) throws PawnException;
	
	public ReminderPara getReminderParaById(UserConfig userConfig,int recordId)throws PawnException;
	public ReminderPara getReminderParaByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllReminderPara(UserConfig userConfig,List<QueryCriteria>criteriaList) throws PawnException;
}
