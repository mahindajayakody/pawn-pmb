package com.m4.pawning.service;

import java.util.List;

import com.m4.core.bean.AuditTrail;
import com.m4.core.bean.UserLog;
import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;

public interface AuditTrailService {

	DataBag getAllAuditTrails(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException;
	
	AuditTrail getAuditTrailByTrnNo(UserConfig userConfig, String trnNo) throws PawnException;;

	DataBag getAuditTrail(UserConfig userConfig, UserLog userLog, AuditTrail auditTrail) throws PawnException;

	List<String> getAuditTrailTypes();

}
