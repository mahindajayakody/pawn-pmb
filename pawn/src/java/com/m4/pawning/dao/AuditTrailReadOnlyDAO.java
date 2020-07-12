package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.bean.AuditTrail;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;

public interface AuditTrailReadOnlyDAO {

	DataBag getAllAuditTrails(UserConfig userConfig, List<QueryCriteria> criteriaList);

	AuditTrail getAuditTrailByTrnNo(UserConfig userConfig, String trnNo);

	DataBag getAuditTrail(UserConfig userConfig, AuditTrail auditTrail);

	List<String> getAuditTrailTypes();
}
