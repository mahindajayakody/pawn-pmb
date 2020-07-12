package com.m4.core.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.m4.core.bean.AuditTrail;
import com.m4.core.bean.UserSession;

public final class AuditTrailDAOImpl implements AuditTrailDAO {

	private static final Logger logger = Logger.getLogger(AuditTrailDAOImpl.class);

	private SessionFactory sessionFactory;

	private void save(AuditTrail auditTrail) {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.save(auditTrail);
		session.flush();
	}

	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void createAuditTrail(AuditTrail auditTrail) {
		logger.debug("**** Enter in to createAuditTrail ****");
		// SessionFactory sessionFactory = ApplicationContextUtil.getAuditSessionFactory();
		// session = sessionFactory.getCurrentSession();
		// session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
//		auditTrail.setTrnNo(UserSession.transactionId.get());
		save(auditTrail);

	}
}
