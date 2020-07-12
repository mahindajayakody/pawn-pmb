package com.m4.core.util;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Restrictions;

import com.m4.core.bean.Serial;
import com.m4.core.bean.SerialMaster;
import com.m4.core.bean.TransactionConsolidate;
import com.m4.core.bean.TransactionTrace;
import com.m4.core.exception.CommonDataAccessException;

public class TransactionDAOSupport extends BaseDAOSupport {
	private static Logger logger = Logger.getLogger(TransactionDAOSupport.class);
	
	protected long getSerialValue(UserConfig userConfig,String serialCode,int productId){
		SerialMaster serialMaster = null;
		Serial serial=null;
		long serialValue=0;
		Criteria criteria = getSession().createCriteria(SerialMaster.class);
		criteria.setLockMode(LockMode.READ);
		criteria.add(Restrictions.eq("serialCode", serialCode));
		
		serialMaster = (SerialMaster)criteria.uniqueResult();
		
		//Check serial master configuration details
		if (serialMaster==null){
			throw new CommonDataAccessException("errors.serial");
		}		
		
		criteria = null;
		criteria = getSession().createCriteria(Serial.class);
		criteria.add(Restrictions.eq("serialCode", serialCode));		
		
		//If serial is annually,Create criteria for current financial year  
		if (serialMaster.getIsAnnually()==1){
			criteria.add(Restrictions.le("financeYearBegin",userConfig.getLoginDate()));
			criteria.add(Restrictions.ge("financeYearEnd",userConfig.getLoginDate()));
		}
		//If serial is branch wise,Create criteria for login branch
		if (serialMaster.getIsBranchWise()==1){
			criteria.add(Restrictions.eq("branchId",userConfig.getBranchId()));
		}
		//If serial is product wise,Create criteria for product
		if (serialMaster.getIsProductWise()==1){			
			criteria.add(Restrictions.eq("productId",productId));
		}
		
		serial = (Serial)criteria.uniqueResult();
		if(serial==null){
			throw new CommonDataAccessException("errors.serialnotfound");
		}
		
		serialValue = serial.getSerialValue();
		serial.setSerialValue(++serialValue);
		getHibernateTemplate().update(serial);
		return serialValue;
	}
		
	public void initializeTransactionDomainObject(UserConfig userConfig, TransactionTrace trace) {
		trace.setUserId(userConfig.getUserId());        
        trace.setLastUpdateDate((userConfig).getLoginDate());
        if(trace instanceof TransactionConsolidate){
        	TransactionConsolidate consolidate = (TransactionConsolidate)trace;
        	consolidate.setCompanyId(userConfig.getCompanyId());
        	consolidate.setBranchId(userConfig.getBranchId());
        }
	}
}
