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
import com.m4.pawning.dao.ReminderParaDAO;
import com.m4.pawning.domain.ReminderPara;

public class ReminderParaDAOImpl extends MasterDAOSupport implements
		ReminderParaDAO {
	public static final Logger logger= Logger.getLogger(ReminderParaDAOImpl.class);

	public void createReminderPara(UserConfig userConfig,
			ReminderPara reminderPara) {
		logger.debug("**** Enter in to createReminderPara ****");
		Criteria criteria= getSession().createCriteria(ReminderPara.class);
		criteria.add(Restrictions.eq("code", reminderPara.getCode()))
				.add(Restrictions.eq("schemeId", reminderPara.getSchemeId()));
		
		criteria.setProjection(Projections.rowCount());
		
		int count = ((Integer)criteria.uniqueResult()).intValue();
		
		if (count!=0)
			throw new CommonDataAccessException("errors.recordexist");
		
		create(userConfig, reminderPara);		
		logger.debug("**** Leaving createReminderPara ****");

	}
	public void removeReminderPara(UserConfig userConfig,
			ReminderPara reminderPara) {
		logger.debug("**** Enter in to removeReminderPara ****");
		ReminderPara remPara = (ReminderPara)getHibernateTemplate().get(ReminderPara.class, Integer.valueOf(reminderPara.getRecordId()));
		remPara.setVersion(reminderPara.getVersion());
		remove(userConfig, remPara);
		logger.debug("**** Leaving removeReminderPara ****");

	}

	public void updateReminderPara(UserConfig userConfig,
			ReminderPara reminderPara) {
		logger.debug("**** Enter in to updateReminderPara ****");
		update(userConfig, reminderPara);		
		logger.debug("**** Leaving updateReminderPara ****");
	}

	public DataBag getAllReminderPara(UserConfig userConfig,
			List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllReminderPara ****");
		DataBag remParaBag=null;
		Criteria criteria = getSession().createCriteria(ReminderPara.class);
		remParaBag = getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.debug("**** Enter in to getAllReminderPara ****");
		return remParaBag;
	}

	public ReminderPara getReminderParaByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getReminderParaByCode ****");

		ReminderPara reminderPara=null;
		Criteria criteria=getSession().createCriteria(ReminderPara.class);
		criteria.add(Restrictions.eq("code", code));
		reminderPara = (ReminderPara)criteria.uniqueResult();
		 
		if(reminderPara==null)
			throw new CommonDataAccessException("errors.recordnotfound");		
		logger.debug("**** Leaving getReminderParaByCode ****");		
		return reminderPara;
	}

	public ReminderPara getReminderParaById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getReminderParaById ****");
		ReminderPara reminderPara=null;
		reminderPara= (ReminderPara)getHibernateTemplate().get(ReminderPara.class, Integer.valueOf(recordId));
		
		if (reminderPara==null)
			throw new CommonDataAccessException("errors.recordnotfound");
			
		logger.debug("**** Leaving getReminderParaById ****");
		
		return reminderPara;
	}


}
