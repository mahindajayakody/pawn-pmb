package com.m4.core.util;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Util class which is aware about the Spring ApplicationContext. Basically exposes spring defined beans to outside.
 * 
 * @author Gayan Priyanatha
 * 
 */
final class ApplicationContextUtil implements ApplicationContextAware {

	private static SessionFactory sessionFactory;
	private static SessionFactory auditSessionFactory;

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		sessionFactory = (SessionFactory) applicationContext.getBean("mySessionFactory");
		auditSessionFactory = ((SessionFactory) applicationContext.getBean("auditSessionFactory"));
	}

	public static final SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static final SessionFactory getAuditSessionFactory() {
		return auditSessionFactory;
	}
}
