package com.m4.core.util;

import com.m4.core.bean.EventLog;
import com.m4.core.util.DataBag;
import com.m4.core.util.UserConfig;

public interface SystemAuditDAO {
	public void createEventLog(UserConfig userConfig,EventLog eventLog);
	public DataBag getEventLogById(UserConfig userConfig,int recordId);
	public DataBag getEventLogByTransactionNo(UserConfig userConfig);
}
