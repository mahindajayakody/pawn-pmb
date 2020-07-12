package com.m4.core.bean;

public final class AuditTrace {

	private final Object entity;
	
	private final AuditTrail auditTrail;

	public AuditTrace(AuditTrail auditTrail, Object entity) {
		this.auditTrail = auditTrail;
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}

	public final AuditTrail getAuditTrail() {
		return auditTrail;
	}

}
