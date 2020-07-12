package com.m4.pawning.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.bean.AccessBranch;
import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.PasswordValidator;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.OfficerDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.OfficerHistory;
import com.m4.pawning.domain.ParameterValue;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.UserGroup;
import com.m4.pawning.util.ParameterEnum;

public class OfficerDAOImpl extends MasterDAOSupport implements OfficerDAO {
	private static final Logger logger = Logger.getLogger(OfficerDAOImpl.class);
	private static final PasswordValidator PASSWORD_VALIDATOR= new PasswordValidator();
	private static final SimpleDateFormat simpdate = new SimpleDateFormat("dd-MM-yyyy");
	String returnValue = null; 
	Calendar calendar = Calendar.getInstance();
	
	public void createOfficer(UserConfig userConfig, Officer officer) { 
		

		logger.debug("**** Enter in to createOfficer method ****");
		Criteria criteria = getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("userName", officer.getUserName()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.setProjection(Projections.rowCount());
		
		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");
        
        //Get parameter value for password Expire Period
  		Criteria paraCriteria = getSession().createCriteria(ParameterValue.class);
  		paraCriteria.add(Restrictions.eq("parameterId", ParameterEnum.PASSWORDEXPIREPERIOD.getCode()));
  		paraCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
  		paraCriteria.addOrder(Order.desc("effDate"));
  		
  		List<ParameterValue> paraValList = paraCriteria.list();
  		if(paraValList.size() == 0){
  			throw new CommonDataAccessException("errors.psswdexpperiod");
  		}
  		
  		int passExpPeriod = Integer.parseInt(paraValList.get(0).getParaValue());
  		//Get parameter value for Password Repeat Time
  		paraCriteria = null;
  		paraValList=null;
  		paraCriteria = getSession().createCriteria(ParameterValue.class);
  		paraCriteria.add(Restrictions.eq("parameterId", ParameterEnum.PASSWORDREPEATTIME.getCode()));
  		paraCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
  		paraCriteria.addOrder(Order.desc("effDate"));
  		
  		paraValList = paraCriteria.list();
  		if(paraValList.size() == 0){
  			throw new CommonDataAccessException("errors.psswdreptcount");
  		}

  		
  		int passExpRepeatTime = Integer.parseInt(paraValList.get(0).getParaValue());
  		
  		Calendar calendar=Calendar.getInstance();
  		calendar.setTime(userConfig.getLoginDate());
  		calendar.add(Calendar.DATE, passExpPeriod);
  		  		
        officer.setBranch((Branch)getSession().get(Branch.class , officer.getBranch().getBranchId()));
        officer.setPawner((Pawner)getSession().get(Pawner.class , officer.getPawner().getPawnerId()));
        officer.setUserGroup((UserGroup)getSession().get(UserGroup.class , officer.getUserGroup().getUserGroupId()));
        officer.setCompanyId(userConfig.getCompanyId());
        officer.setPasswordRepatTime(passExpRepeatTime);
        officer.setPasswordValidPeriod(calendar.getTime());

//        initializeDomainObject(userConfig, officer);
//        getHibernateTemplate().save(officer);
        create(userConfig, officer);
        
        for (AccessBranch accBranch:officer.getAccessBranchCollection()){
        	accBranch.setOfficerId(officer.getOfficerId());
        	getHibernateTemplate().save(accBranch);
        }
        
		logger.debug("**** Leaving from createOfficer method ****");
	}

	public void removeOfficer(UserConfig userConfig, Officer officer) {
		logger.debug("**** Enter in to removeOfficer method ****");
//		Officer status = (Officer)getHibernateTemplate().get(Officer.class, Integer.valueOf(officer.getRecordId()));
//		status.setVersion(officer.getVersion());
//		getHibernateTemplate().delete(status);
		remove(userConfig, officer);
		logger.debug("**** Leaving from removeOfficer method ****");
	}

	public void updateOfficer(UserConfig userConfig, Officer officer) {
		logger.debug("**** Enter in to updateOfficer method ****");
        officer.setBranch((Branch)getSession().get(Branch.class , officer.getBranch().getBranchId()));
        officer.setPawner((Pawner)getSession().get(Pawner.class , officer.getPawner().getPawnerId()));
        officer.setUserGroup((UserGroup)getSession().get(UserGroup.class , officer.getUserGroup().getUserGroupId()));
		officer.setCompanyId(userConfig.getCompanyId());
		
		
		
//		initializeDomainObject(userConfig, officer);
//		getHibernateTemplate().update(officer);
		update(userConfig, officer);
		
		Query query = getSession().createQuery("DELETE FROM AccessBranch WHERE officerId=:officerId");
		query.setInteger("officerId", officer.getOfficerId());
		query.executeUpdate();
		
		for (AccessBranch accBranch:officer.getAccessBranchCollection()){
        	accBranch.setOfficerId(officer.getOfficerId());
        	getHibernateTemplate().save(accBranch);
        }
		
		logger.debug("**** Leaving from updateOfficer method ****");
	}

	public Officer getOfficerById(UserConfig userConfig,int recordId){
		logger.debug("**** Enter in to getOfficerById method ****");
		Officer officer = null;
		Criteria criteria = getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("officerId", recordId));
		criteria.setFetchMode("pawner", FetchMode.JOIN);
		criteria.setFetchMode("branch", FetchMode.JOIN);
		criteria.setFetchMode("userGroup", FetchMode.JOIN);
		criteria.setFetchMode("accessBranchCollection", FetchMode.JOIN);
		officer = (Officer)criteria.uniqueResult();

		if(officer == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getOfficerById method ****");
		return officer;
	}

	public Officer getOfficerByUserName(UserConfig userConfig,String code){
		logger.debug("**** Enter in to getOfficerByUserName method ****");
		Officer officer = null;
		Criteria criteria = getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("userName", code));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.setFetchMode("pawner", FetchMode.JOIN);
		criteria.setFetchMode("branch", FetchMode.JOIN);
		criteria.setFetchMode("userGroup", FetchMode.JOIN);
		criteria.setFetchMode("accessBranchCollection", FetchMode.JOIN);

		officer = (Officer)criteria.uniqueResult();

		if(officer == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getOfficerByUserName method ****");
		return officer;
	}

	public DataBag getAllOfficer(UserConfig userConfig,List<QueryCriteria> criteriaList){
		logger.debug("**** Enter in to getAllOfficer method ****");
		DataBag officerBag = null;
		Criteria criteria = getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.setFetchMode("pawner", FetchMode.JOIN);
		criteria.setFetchMode("branch", FetchMode.JOIN);
		criteria.setFetchMode("userGroup", FetchMode.JOIN);
		criteria.setFetchMode("accessBranchCollection", FetchMode.JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		officerBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllOfficer method ****");
		return officerBag;
	}
	public List<Officer> getAllOfficer(UserConfig userConfig){
		logger.debug("**** Enter in to getAllOfficer method ****");
		List<Officer> officerBag = null;
//		Criteria criteria = getSession().createCriteria(Officer.class);
//		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
//		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
//		criteria.setFetchMode("pawner", FetchMode.JOIN);
//		criteria.setFetchMode("branch", FetchMode.JOIN);
//		criteria.setFetchMode("userGroup", FetchMode.JOIN);
//		criteria.setFetchMode("accessBranchCollection", FetchMode.JOIN);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		
//		officerBag = criteria.list();
		logger.debug("**** Leaving from getAllOfficer method ****");
		return officerBag;
	}

	public Officer getOfficerByPawnerId(UserConfig userConfig,int pawnerId){
		logger.debug("**** Enter in to getOfficerByPawnerId method ****");
		Officer officer = null;
		Criteria criteria = getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("pawner.pawnerId", pawnerId));
		criteria.setFetchMode("pawner", FetchMode.JOIN);
		officer = (Officer)criteria.uniqueResult();

		if(officer == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getOfficerByPawnerId method ****");
		return officer;
	}

	@Override
	public void resetPassword(UserConfig userConfig, Officer officer) {
		logger.debug("**** Enter in to changePassword method ****");
		
        //Get parameter value for password Expire Period
  		Criteria paraCriteria = getSession().createCriteria(ParameterValue.class);
  		paraCriteria.add(Restrictions.eq("parameterId", ParameterEnum.PASSWORDEXPIREPERIOD.getCode()));
  		paraCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
  		paraCriteria.addOrder(Order.desc("effDate"));
  		
  		List<ParameterValue> paraValList = paraCriteria.list();
  		if(paraValList.size() == 0){
  			throw new CommonDataAccessException("errors.psswdexpperiod");
  		}
  		
  		int passExpPeriod = Integer.parseInt(paraValList.get(0).getParaValue());
  		//Get parameter value for Password Repeat Time
  		paraCriteria = null;
  		paraValList=null;
  		paraCriteria = getSession().createCriteria(ParameterValue.class);
  		paraCriteria.add(Restrictions.eq("parameterId", ParameterEnum.PASSWORDREPEATTIME.getCode()));
  		paraCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
  		paraCriteria.addOrder(Order.desc("effDate"));
  		
  		paraValList = paraCriteria.list();
  		if(paraValList.size() == 0){
  			throw new CommonDataAccessException("errors.psswdreptcount");
  		}

  		
  		int passExpRepeatTime = Integer.parseInt(paraValList.get(0).getParaValue());
  		
  		Calendar calendar=Calendar.getInstance();
  		calendar.setTime(userConfig.getLoginDate());
  		calendar.add(Calendar.DATE, passExpPeriod);
  		
  		officer.setPasswordRepatTime(passExpRepeatTime);
  		officer.setPasswordValidPeriod(calendar.getTime());
  		
		update(userConfig, officer);
		this.createHistory(userConfig,officer);
		logger.debug("**** Leaving from changePassword method ****");
	}

	private void createHistory(UserConfig userConfig, Officer officer) {
		logger.debug("**** Enter in to createHistory method ****");
		OfficerHistory history=new OfficerHistory();
		history.setOfficerId(officer.getOfficerId());
		history.setPassword(officer.getPassword());
//		initializeDomainObject(userConfig, history);
		getHibernateTemplate().merge(history);
		logger.debug("**** Leaving from createHistory method ****");
		
	}

	@Override
	public List<OfficerHistory> validatePreviousPassword(Officer officer) {
		logger.debug("**** Enter in to validatePreviousPassword method ****");
		Criteria criteria=getSession().createCriteria(OfficerHistory.class);
		criteria.add(Restrictions.eq("officerId", officer.getOfficerId()));
		criteria.addOrder(Order.desc("historyId"));
		criteria.setFetchSize(3);
		logger.debug("**** Leaving from validatePreviousPassword method ****");
		return criteria.list();
	}

	@Override
	public void changePassword(UserConfig userConfig, Officer officer) {
		logger.debug("**** Enter in to changePassword method ****");
        //Get parameter value for password Expire Period
  		Criteria paraCriteria = getSession().createCriteria(ParameterValue.class);
  		paraCriteria.add(Restrictions.eq("parameterId", ParameterEnum.PASSWORDEXPIREPERIOD.getCode()));
  		paraCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
  		paraCriteria.addOrder(Order.desc("effDate"));
  		
  		List<ParameterValue> paraValList = paraCriteria.list();
  		if(paraValList.size() == 0){
  			throw new CommonDataAccessException("errors.psswdexpperiod");
  		}
  		
  		int passExpPeriod = Integer.parseInt(paraValList.get(0).getParaValue());
  		//Get parameter value for Password Repeat Time
  		paraCriteria = null;
  		paraValList=null;
  		paraCriteria = getSession().createCriteria(ParameterValue.class);
  		paraCriteria.add(Restrictions.eq("parameterId", ParameterEnum.PASSWORDREPEATTIME.getCode()));
  		paraCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
  		paraCriteria.addOrder(Order.desc("effDate"));
  		
  		paraValList = paraCriteria.list();
  		if(paraValList.size() == 0){
  			throw new CommonDataAccessException("errors.psswdreptcount");
  		}

  		
  		int passExpRepeatTime = Integer.parseInt(paraValList.get(0).getParaValue());
  		
  		Calendar calendar=Calendar.getInstance();
  		calendar.setTime(userConfig.getLoginDate());
  		calendar.add(Calendar.DATE, passExpPeriod);
  		officer.setPasswordValidPeriod(calendar.getTime());
  		officer.setPasswordRepatTime(passExpRepeatTime);
  		
		getHibernateTemplate().update(officer);
		this.createHistory(userConfig,officer);
		logger.debug("**** Leaving from changePassword method ****");
	}

	@Override
	public List<Officer> getAllActiveOfficer(UserConfig userConfig) {
		List<Officer> officers =null;
		Criteria criteria=getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("isActive", 1));
		officers = criteria.list();
		return officers;
	}


}
