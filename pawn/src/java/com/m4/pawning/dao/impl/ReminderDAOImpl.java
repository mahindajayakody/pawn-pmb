package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ReminderDAO;
import com.m4.pawning.domain.Reminder;

public class ReminderDAOImpl extends MasterDAOSupport implements ReminderDAO {

	private static final Logger logger= Logger.getLogger(ReminderDAOImpl.class);
	public DataBag getAllReminder(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.info("**** Enter in to getAllReminder ****");
		DataBag reminderBag=null;
		Criteria criteria = getSession().createCriteria(Reminder.class)
							.add(Restrictions.eq("companyId", userConfig.getCompanyId()))
							.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		try {
			reminderBag=getDataList(getHibernateTemplate(), criteriaList, criteria);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("**** Leaving getAllReminder ****");
		return reminderBag;
	}
	public Reminder getReminderById(UserConfig userConfig, int recordId) {
		logger.info("**** Enter in to getReminderById ****");
		Reminder reminder=null;
		reminder=(Reminder)getHibernateTemplate().get(Reminder.class, Integer.valueOf(recordId));
		
		if(reminder==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.info("**** Leaving getReminderById ****");
		
		return reminder;
	}

	public void updateReminder(UserConfig userConfig, Reminder reminder) {
		logger.info("**** Enter in to updateReminder ****");
		update(userConfig, reminder);
		logger.info("**** Leaving updateReminder ****");
		
	}
	public Reminder getReminderByCode(UserConfig userConfig, String code) {
		logger.info("**** Enter in to getReminderByCode ****");
		Reminder reminder=null;
		reminder=(Reminder)getHibernateTemplate().get(Reminder.class, code);
		
		if(reminder==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.info("**** Leaving getReminderByCode ****");
		
		return reminder;
	}
	
	

}
