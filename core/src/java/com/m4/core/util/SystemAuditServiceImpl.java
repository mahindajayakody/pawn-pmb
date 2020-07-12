package com.m4.core.util;

import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.UserConfig;
import com.m4.core.bean.EventLog;

public class SystemAuditServiceImpl implements SystemAuditService {
	private SystemAuditDAO systemAuditDAO;
	

	public void setSystemAuditDAO(SystemAuditDAO systemAuditDAO) {
		this.systemAuditDAO = systemAuditDAO;
	}

	public void createEventLog(UserConfig userConfig, EventLog eventLog) throws PawnException{
		systemAuditDAO.createEventLog(userConfig, eventLog);
	}

	public DataBag getEventLogById(UserConfig userConfig, int recordId) throws PawnException{
		return systemAuditDAO.getEventLogById(userConfig, recordId);
	}

	public DataBag getEventLogByTransactionNo(UserConfig userConfig) throws PawnException{
		return systemAuditDAO.getEventLogByTransactionNo(userConfig);
	}
}
