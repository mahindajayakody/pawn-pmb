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
import com.m4.pawning.dao.DueFromDAO;
import com.m4.pawning.domain.DueFrom;


public class DueFromDAOImpl extends MasterDAOSupport implements DueFromDAO {
	private static final Logger logger = Logger.getLogger(DueFromDAOImpl.class);
	
	public void createDueFrom(UserConfig userConfig, DueFrom dueFrom) {
		logger.debug("**** Enter in to createDueFrom method ****");
		Criteria criteria = getSession().createCriteria(DueFrom.class);
		criteria.add(Restrictions.eq("ticketId", dueFrom.getTicketId()));
		criteria.add(Restrictions.eq("dueTypeId", dueFrom.getDueTypeId()));
		criteria.setProjection(Projections.rowCount());
		
		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");
		
//        initializeDomainObject(userConfig, dueFrom);
        getHibernateTemplate().save(dueFrom);
//        create(userConfig, dueFrom);
		logger.debug("**** Leaving from createDueFrom method ****");
	}

	public DataBag getAllDueFrom(UserConfig userConfig, List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllDueFrom method ****");
		DataBag dueFromBag = null;		
		Criteria criteria = getSession().createCriteria(DueFrom.class);
		dueFromBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllDueFrom method ****");
		return dueFromBag;
	}
	public DueFrom getDueFromById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getDueFromById method ****");
		DueFrom dueFrom = null;
		dueFrom = (DueFrom)getHibernateTemplate().get(DueFrom.class, Integer.valueOf(recordId));
		
		if(dueFrom == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getDueFromById method ****");
		return dueFrom;
	}

	public DataBag getAllDueFromByTicket(UserConfig userConfig, int ticketId) {
		logger.debug("**** Enter in to getAllDueFrom method ****");
		DataBag dueFromBag = null;		
		Criteria criteria = getSession().createCriteria(DueFrom.class);
		criteria.add(Restrictions.eq("ticketId", ticketId));
		dueFromBag = (DataBag) criteria.list();
		logger.debug("**** Leaving from getAllDueFrom method ****");
		return dueFromBag;
	}
}
