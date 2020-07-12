package com.m4.pawning.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LockMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.bean.Serial;
import com.m4.core.bean.SerialMaster;
import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.BranchDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.SystemDate;


public class BranchDAOImpl extends MasterDAOSupport implements BranchDAO {
	private static final Logger logger = Logger.getLogger(BranchDAOImpl.class);
	
	public void createBranch(UserConfig userConfig, Branch branch) {
		logger.debug("**** Enter in to createBranch method ****");
		Criteria criteria = getSession().createCriteria(Branch.class);
		criteria.add(Restrictions.eq("code", branch.getCode()));
		criteria.setProjection(Projections.rowCount());
		
		int count = ((Integer) criteria.uniqueResult());
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");
        
        branch.setUserId(userConfig.getUserId());
        branch.setLastUpdateDate((userConfig).getLoginDate());
        branch.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
        getHibernateTemplate().save(branch);
        
        //Create record for SystemDate
        Calendar calendar = Calendar.getInstance();
		calendar.setTime(userConfig.getLoginDate());
		calendar.add(Calendar.DATE, -1);
        
        SystemDate systemDate = new SystemDate();
        systemDate.setBranchId(branch.getBranchId());
        systemDate.setCompanyId(userConfig.getCompanyId());
        systemDate.setPreviousDate(calendar.getTime());
        systemDate.setCurrentDate(userConfig.getLoginDate());
        calendar.add(Calendar.DATE, 2);
        systemDate.setNextDate(calendar.getTime());
        systemDate.setLastUpdateUserId(userConfig.getUserId());
        
        getHibernateTemplate().save(systemDate);
        branch.setSystemDate(systemDate);
        
        //Create serial record to the just initiated branch
        Criteria serialCrt = getSession().createCriteria(SerialMaster.class);
        serialCrt.setLockMode(LockMode.READ);
        List<SerialMaster> seriaList = serialCrt.list();
        
        calendar = Calendar.getInstance();
		calendar.setTime(userConfig.getLoginDate());
        int financeYear = calendar.get(Calendar.YEAR);
        
        calendar.set(financeYear, Calendar.JANUARY, 1);
        Date financeYearBegin = calendar.getTime();
        
        calendar.set(financeYear, Calendar.DECEMBER, 31);
        Date financeYearEnd = calendar.getTime();
        
        for (SerialMaster master : seriaList){
//        	if (master.getIsAnnually() == 1){
        		Serial serial = new Serial();
        		serial.setCompanyId(userConfig.getCompanyId());
        		serial.setFinanceYear(financeYear);
        		serial.setFinanceYearBegin(financeYearBegin);
        		serial.setFinanceYearEnd(financeYearEnd);
        		serial.setSerialCode(master.getSerialCode());
        		serial.setSerialValue(0);
        		serial.setBranchId(branch.getBranchId());
        		serial.setProductId(1);
        		
        		getHibernateTemplate().save(serial);
//        	}
        }
        
		logger.debug("**** Leaving from createBranch method ****");
	}

	public void removeBranch(UserConfig userConfig, Branch branch) {
		logger.debug("**** Enter in to removeBranch method ****");
		Branch status = (Branch)getHibernateTemplate().get(Branch.class, Integer.valueOf(branch.getRecordId()));
		status.setVersion(branch.getVersion());
		getHibernateTemplate().delete(getHibernateTemplate().get(SystemDate.class, branch.getSystemDate().getSystemDateId()));
		getHibernateTemplate().delete(status);
		logger.debug("**** Leaving from removeBranch method ****");
	}

	public void updateBranch(UserConfig userConfig, Branch branch) {
		logger.debug("**** Enter in to updateBranch method ****");
		branch.setUserId(userConfig.getUserId());
		branch.setLastUpdateDate((userConfig).getLoginDate());
		branch.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		branch.setSystemDate((SystemDate)getSession().createCriteria(SystemDate.class)
				 									 .add(Restrictions.eq("branchId", branch.getBranchId()))
				 									 .uniqueResult());
		
		getHibernateTemplate().update(branch);
		logger.debug("**** Leaving from updateBranch method ****");		
	}
	
	public DataBag getAllBranch(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllBranch method ****");
		DataBag branchBag = null;		
		Criteria criteria = getSession().createCriteria(Branch.class);
		criteria.setFetchMode("systemDate", FetchMode.JOIN);
		branchBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllBranch method ****");
		return branchBag;
	}

	public Branch getBranchByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getBranchByCode method ****");
		Branch branch = null;
		Criteria criteria = getSession().createCriteria(Branch.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.setFetchMode("systemDate", FetchMode.JOIN);
		branch = (Branch)criteria.uniqueResult();
		
		if(branch == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getBranchByCode method ****");
		return branch;
	}

	public Branch getBranchById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getBranchById method ****");
		Branch branch = null;
		branch = (Branch)getHibernateTemplate().get(Branch.class, Integer.valueOf(recordId));		
		
		if(branch == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getBranchById method ****");
		return branch;
	}
	
	public Branch getBranchByIdWithSystemDate(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getBranchByIdWithSystemDate method ****");
		Branch branch = null;
		
		Criteria criteria = getSession().createCriteria(Branch.class);
		criteria.add(Restrictions.eq("branchId", recordId));
		criteria.setFetchMode("systemDate", FetchMode.JOIN);
		branch = (Branch)criteria.uniqueResult();
		
		if(branch == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getBranchByIdWithSystemDate method ****");
		return branch;
	}
}
