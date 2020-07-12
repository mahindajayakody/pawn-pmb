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
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.TicketStatusDAO;
import com.m4.pawning.domain.TicketStatus;

public class TicketStatusDAOImpl extends MasterDAOSupport implements TicketStatusDAO {
	private static final Logger logger = Logger.getLogger(TicketStatusDAOImpl.class);

	public void createTicketStatus(UserConfig userConfig, TicketStatus ticketStatus) {
		logger.debug("**** Enter in to createTicketStatus method ****");
		Criteria criteria = getSession().createCriteria(TicketStatus.class);
		criteria.add(Restrictions.eq("code", ticketStatus.getCode()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        ticketStatus.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
//        initializeDomainObject(userConfig, ticketStatus);
//        getHibernateTemplate().save(ticketStatus);
        create(userConfig, ticketStatus);
		logger.debug("**** Leaving from createTicketStatus method ****");
	}

	public void removeTicketStatus(UserConfig userConfig, TicketStatus ticketStatus) {
		logger.debug("**** Enter in to removeTicketStatus method ****");
//		TicketStatus status = (TicketStatus)getHibernateTemplate().get(TicketStatus.class, Integer.valueOf(ticketStatus.getRecordId()));
//		status.setVersion(ticketStatus.getVersion());
//		getHibernateTemplate().delete(status);
		remove(userConfig, ticketStatus);
		logger.debug("**** Leaving from removeTicketStatus method ****");
	}

	public void updateTicketStatus(UserConfig userConfig, TicketStatus ticketStatus) {
		logger.debug("**** Enter in to updateTicketStatus method ****");
//		ticketStatus.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
//		initializeDomainObject(userConfig, ticketStatus);
//		getHibernateTemplate().update(ticketStatus);
		update(userConfig, ticketStatus);
		logger.debug("**** Leaving from updateTicketStatus method ****");
	}

	public TicketStatus geTicketStatusById(UserConfig userConfig,int recordId){
		logger.debug("**** Enter in to geTicketStatusById method ****");
		TicketStatus ticketStatus = null;
		ticketStatus = (TicketStatus)getHibernateTemplate().get(TicketStatus.class, Integer.valueOf(recordId));

		if(ticketStatus == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from geTicketStatusById method ****");
		return ticketStatus;
	}

	public TicketStatus geTicketStatusByCode(UserConfig userConfig,String code){
		logger.debug("**** Enter in to geTicketStatusById method ****");
		TicketStatus ticketStatus = null;
		Criteria criteria = getSession().createCriteria(TicketStatus.class);
		criteria.add(Restrictions.eq("code", code));
		ticketStatus = (TicketStatus)criteria.uniqueResult();

		if(ticketStatus == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from geTicketStatusById method ****");
		return ticketStatus;
	}

	public DataBag getAllTicketStatus(UserConfig userConfig,List<QueryCriteria> criteriaList){
		logger.debug("**** Enter in to getAllTicketStatus method ****");
		DataBag ticketStatus = null;
		Criteria criteria = getSession().createCriteria(TicketStatus.class);
		ticketStatus = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllTicketStatus method ****");
		return ticketStatus;
	}


}
