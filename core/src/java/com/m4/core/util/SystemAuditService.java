package com.m4.core.util;


import com.m4.core.bean.EventLog;
import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.UserConfig;


public interface SystemAuditService {
	public void createEventLog(UserConfig userConfig,EventLog eventLog) throws PawnException;
	public DataBag getEventLogById(UserConfig userConfig,int recordId)throws PawnException;
	public DataBag getEventLogByTransactionNo(UserConfig userConfig)throws PawnException;

}
