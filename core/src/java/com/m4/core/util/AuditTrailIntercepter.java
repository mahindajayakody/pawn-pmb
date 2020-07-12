package com.m4.core.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.m4.core.bean.AuditTrace;
import com.m4.core.bean.AuditTrail;
import com.m4.core.bean.Auditable;
import com.m4.core.bean.Trace;

public class AuditTrailIntercepter extends EmptyInterceptor {
	private static final String COLUMN_SEPERATER = " | ";

	private static final long serialVersionUID = 1L;

	private static final String INSERT = "INSERT";

	private static final String UPDATE = "UPDATE";

	private static final String DELETE = "DELETE";

	private final Set<AuditTrace> inserts = new HashSet<AuditTrace>();

	private final Set<AuditTrace> updates = new HashSet<AuditTrace>();

	private final Set<AuditTrace> deletes = new HashSet<AuditTrace>();

	private AuditTrailDAO auditTrailDAO;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		if (entity instanceof Auditable) {
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setPrimarykey(id.toString());
			populateSimpleAuditData(auditTrail, state);
			AuditTrace auditTrace = new AuditTrace(auditTrail, entity);
			inserts.add(auditTrace);
			return true;
		}
		return false;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {
		if (entity instanceof Auditable) {

			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setPrimarykey(id.toString());
			populateUpdateAuditData(auditTrail, currentState, previousState);
			AuditTrace auditTrace = new AuditTrace(auditTrail, entity);
			updates.add(auditTrace);
			return true;
		}
		return false;

	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setPrimarykey(id.toString());
			populateSimpleAuditData(auditTrail, state);
			AuditTrace auditTrace = new AuditTrace(auditTrail, entity);
			deletes.add(auditTrace);
		}
	}

	@Override
	public void postFlush(Iterator entities) {
		try {

			for (AuditTrace auditTrace : inserts) {
				populateAuditTrail(auditTrace, INSERT, entities);
			}

			for (AuditTrace auditTrace : updates) {
				populateAuditTrail(auditTrace, UPDATE, entities);
			}

			for (AuditTrace auditTrace : deletes) {
				populateAuditTrail(auditTrace, DELETE, entities);
			}

		} finally {
			inserts.clear();
			updates.clear();
			deletes.clear();
		}
	}

	public void populateAuditTrail(final AuditTrace auditTrace, final String action, final Iterator entities) {
		try {
			for (; entities.hasNext();) {
				AuditTrail auditTrail = auditTrace.getAuditTrail();
				
				Object entity = entities.next();
				if (entity instanceof Trace) {
					Trace myClass = (Trace) entity;
					String entityName = (entity.getClass()).toString();
					auditTrail.setTableName(entityName.substring(entityName.lastIndexOf(".") + 1, entityName.length()));
					auditTrail.setTrnNo(myClass.getTransactionId());
					auditTrail.setAction(action);
					auditTrail.setTrandate(myClass.getLastUpdateDate());
					
					auditTrailDAO.createAuditTrail(auditTrail);					
				}
				
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
	}

	public final void setAuditTrailDAO(final AuditTrailDAO auditTrailDAO) {
		this.auditTrailDAO = auditTrailDAO;
	}

	private void populateUpdateAuditData(AuditTrail auditTrail, final Object[] currentState,
			final Object[] previousState) {
		String afterSaveData = new String();
		if(currentState != null) { 
			for (int i = 0; i < currentState.length; i++) {
				if (currentState[i] == null) {
					if (previousState != null) {
						afterSaveData = afterSaveData + COLUMN_SEPERATER + previousState[i];
					}
				} else {
					afterSaveData = afterSaveData + COLUMN_SEPERATER + currentState[i];
				}
			}
		}
		auditTrail.setAfterSaveData(afterSaveData);

		String beforeSaveData = new String();
		if(previousState != null) { 
			for (int i = 0; i < previousState.length; i++) {
				beforeSaveData = beforeSaveData + COLUMN_SEPERATER + previousState[i];
			}
		}
		auditTrail.setBeforeSaveData(beforeSaveData);
	}

	private void populateSimpleAuditData(AuditTrail auditTrail, final Object[] currentState) {
		String afterSaveData = new String();
		for (int i = 0; i < currentState.length; i++) {
			afterSaveData = afterSaveData + COLUMN_SEPERATER + currentState[i];
		}
		auditTrail.setAfterSaveData(afterSaveData);
	}

}