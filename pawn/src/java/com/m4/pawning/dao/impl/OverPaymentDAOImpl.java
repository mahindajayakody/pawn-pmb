package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.OverPaymentDAO;
import com.m4.pawning.domain.OverPayment;


public class OverPaymentDAOImpl extends MasterDAOSupport implements OverPaymentDAO {
	private static final Logger logger = Logger.getLogger(OverPaymentDAOImpl.class);
	
	public void createOverPayment(UserConfig userConfig, OverPayment overPayment) {
		logger.debug("**** Enter in to createOverPayment method ****");
		Criteria criteria = getSession().createCriteria(OverPayment.class);
		criteria.add(Restrictions.eq("ticketId", overPayment.getTicketId()));
		criteria.add(Restrictions.eq("dueTypeId", overPayment.getDueTypeId()));
		criteria.setProjection(Projections.rowCount());
		
		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");
		
//        initializeDomainObject(userConfig, overPayment);
        getHibernateTemplate().save(overPayment);
//        create(userConfig, overPayment);
		logger.debug("**** Leaving from createOverPayment method ****");
	}

	public DataBag getAllOverPayment(UserConfig userConfig, List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllOverPayment method ****");
		DataBag overPaymentBag = null;		
		Criteria criteria = getSession().createCriteria(OverPayment.class);
		overPaymentBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllOverPayment method ****");
		return overPaymentBag;
	}
	public OverPayment getOverPaymentById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getOverPaymentById method ****");
		OverPayment overPayment = null;
		overPayment = (OverPayment)getHibernateTemplate().get(OverPayment.class, Integer.valueOf(recordId));
		
		if(overPayment == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getOverPaymentById method ****");
		return overPayment;
	}

	public DataBag getAllOverPaymentByTicket(UserConfig userConfig, int ticketId) {
		logger.debug("**** Enter in to getAllOverPayment method ****");
		DataBag overPaymentBag = null;		
		Criteria criteria = getSession().createCriteria(OverPayment.class);
		criteria.add(Restrictions.eq("ticketId", ticketId));
		overPaymentBag = (DataBag) criteria.list();
		logger.debug("**** Leaving from getAllOverPayment method ****");
		return overPaymentBag;
	}

	/* (non-Javadoc)
	 * @see com.m4.pawning.dao.OverPaymentDAO#getAllOverPaymentByReceipt(com.m4.core.util.UserConfig, int)
	 */
	public DataBag getAllOverPaymentByReceipt(UserConfig userConfig,
			int receiptId) {
		logger.debug("**** Enter in to getAllOverPaymentByReceipt method ****");
		DataBag overPaymentBag = null;		
		Criteria criteria = getSession().createCriteria(OverPayment.class);
		criteria.add(Restrictions.eq("reciptNo", receiptId));
		overPaymentBag = (DataBag) criteria.list();
		logger.debug("**** Leaving from getAllOverPaymentByReceipt method ****");
		return overPaymentBag;
	}
	
}
