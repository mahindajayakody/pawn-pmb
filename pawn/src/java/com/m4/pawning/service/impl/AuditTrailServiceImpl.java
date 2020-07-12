package com.m4.pawning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.m4.core.bean.AuditTrail;
import com.m4.core.bean.UserLog;
import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AuditTrailReadOnlyDAO;
import com.m4.pawning.dao.ProgramAccessDAO;
import com.m4.pawning.service.AuditTrailService;

public class AuditTrailServiceImpl implements AuditTrailService {
	private static final Logger logger = Logger.getLogger(AuditTrailServiceImpl.class);
	private AuditTrailReadOnlyDAO auditTrailReadOnlyDAO;
	private ProgramAccessDAO programAccessDAO;
	private static List<String> auditTrailTypes;

	public final void setAuditTrailReadOnlyDAO(AuditTrailReadOnlyDAO auditTrailReadOnlyDAO) {
		this.auditTrailReadOnlyDAO = auditTrailReadOnlyDAO;
	}

	public final void setProgramAccessDAO(ProgramAccessDAO programAccessDAO) {
		this.programAccessDAO = programAccessDAO;
	}

	@Override
	public DataBag getAllAuditTrails(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return auditTrailReadOnlyDAO.getAllAuditTrails(userConfig, criteriaList);
	}

	@Override
	public AuditTrail getAuditTrailByTrnNo(UserConfig userConfig, String trnNo) throws PawnException {
		return auditTrailReadOnlyDAO.getAuditTrailByTrnNo(userConfig, trnNo);
	}

	@Override
	public DataBag getAuditTrail(UserConfig userConfig, UserLog userLog, AuditTrail auditTrail) throws PawnException {
		logger.debug("**** Enter in to getAuditTrail method ****");
		List<UserLog> UserLogs = programAccessDAO.getAllTransactionsForUser(userLog);
		DataBag auditTrailDataBags = new DataBag();
		List<AuditTrail> auditTrails = new ArrayList<AuditTrail>();
		if(UserLogs != null && !(UserLogs.isEmpty())) {
			for (UserLog userLogTemp:UserLogs) {
				auditTrail.setTrnNo(userLogTemp.getTransactionId());
				auditTrails.addAll(auditTrailReadOnlyDAO.getAuditTrail(userConfig, auditTrail).getDataList());
			}
	
		}
		auditTrailDataBags.setDataList(auditTrails);
		auditTrailDataBags.setCount(auditTrails.size());
		logger.debug("**** Leaving from getAuditTrail method ****");
		return auditTrailDataBags;
	}

	@Override
	public List<String> getAuditTrailTypes() {
		if (auditTrailTypes == null) {
			auditTrailTypes = auditTrailReadOnlyDAO.getAuditTrailTypes();
		}
		return auditTrailTypes;
	}


}
