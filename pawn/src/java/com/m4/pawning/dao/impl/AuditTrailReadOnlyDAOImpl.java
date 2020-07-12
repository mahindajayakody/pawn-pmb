package com.m4.pawning.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;

import com.m4.core.bean.AuditTrail;
import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AuditTrailReadOnlyDAO;

public final class AuditTrailReadOnlyDAOImpl extends MasterDAOSupport implements AuditTrailReadOnlyDAO {

	private static final Logger logger = Logger.getLogger(AuditTrailReadOnlyDAOImpl.class);

	@Override
	public DataBag getAllAuditTrails(UserConfig userConfig, List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllAuditTrails method ****");
		DataBag auditTrailBag = null;
		Criteria criteria = getSession().createCriteria(AuditTrail.class);
		auditTrailBag = getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.debug("**** Leaving from getAllAuditTrails method ****");
		return auditTrailBag;
	}

	@Override
	public AuditTrail getAuditTrailByTrnNo(UserConfig userConfig, String trnNo) {
		logger.debug("**** Enter in to getAuditTrailByTrnNo method ****");
		AuditTrail auditTrail = null;

		Criteria criteria = getSession().createCriteria(AuditTrail.class);
		criteria.add(Restrictions.eq("trnNo", trnNo));
		auditTrail = (AuditTrail) criteria.uniqueResult();

		if (auditTrail == null) throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getAuditTrailByTrnNo method ****");
		return auditTrail;
	}

	@Override
	public DataBag getAuditTrail(UserConfig userConfig, AuditTrail auditTrail) {
		logger.debug("**** Enter in to getAuditTrail method ****");

		Criteria criteria = getSession().createCriteria(AuditTrail.class);
		if (auditTrail.getTrnNo() != null) {
			criteria.add(Restrictions.eq("trnNo", auditTrail.getTrnNo()));
		}

		if (auditTrail.getTableName() != null) {
			criteria.add(Restrictions.eq("tableName", auditTrail.getTableName()));
		}

		if (auditTrail.getAction() != null) {
			criteria.add(Restrictions.eq("action", auditTrail.getAction()));
		}

		if (auditTrail.getTrandate() != null) {
			criteria.add(Restrictions.ge("trandate", auditTrail.getTrandate()));
			criteria.add(Restrictions.le("trandate",
					new Date(auditTrail.getTrandate().getTime() + TimeUnit.DAYS.toMillis(1))));
		}

		if (auditTrail.getFromDate() != null && auditTrail.getToDate() != null) {
			criteria.add(Restrictions.ge("trandate", auditTrail.getFromDate()));
			criteria.add(Restrictions.le("trandate", auditTrail.getToDate()));
		}

		DataBag auditTrailBag = new DataBag();
		auditTrailBag.setDataList(criteria.list());

		// Get total no of result
		criteria.setFirstResult(0);
		criteria.setProjection(Projections.rowCount());

		Object returnValue = criteria.uniqueResult();

		if (returnValue != null) {
			auditTrailBag.setCount((Integer) returnValue);
		}

		logger.debug("**** Leaving from getAuditTrail method ****");
		return auditTrailBag;
	}

	@Override
	public List<String> getAuditTrailTypes() {
		List<String> types = new ArrayList<String>();
		SessionFactory sessionFactory = getHibernateTemplate().getSessionFactory();
		Map<String, ClassMetadata>  map = sessionFactory.getAllClassMetadata();
	    for(String entityName : map.keySet()){
	        types.add(entityName.substring(entityName.lastIndexOf(".") + 1, entityName.length()));
	    }
	    Collections.sort(types);
		return types;
	}

}
