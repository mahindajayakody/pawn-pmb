package com.m4.pawning.dao.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.m4.core.bean.AccessBranch;
import com.m4.core.bean.UserLog;
import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ProgramAccessDAO;
import com.m4.pawning.domain.Company;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.ProgramAccess;
import com.m4.pawning.domain.SystemProgram;
import com.m4.pawning.dto.ProgramAccessDTO;
import com.m4.pawning.util.UserStatusEnum;


public class ProgramAccessDAOImpl extends MasterDAOSupport implements ProgramAccessDAO {
	private static final Logger logger = Logger.getLogger(ProgramAccessDAOImpl.class);

	public boolean createOrUpdateOfficerAccess(UserConfig userConfig, List<ProgramAccess> accessList) {
		logger.debug("**** Enter in to createOrUpdateOfficerAccess method ****");
		for(ProgramAccess programAccess:accessList){
			programAccess.setSystemProgram((SystemProgram)getHibernateTemplate().get(SystemProgram.class, Integer.valueOf(programAccess.getSystemProgram().getSystemProgramId())));
			programAccess.setUserId(userConfig.getUserId());
			programAccess.setLastUpdateDate((userConfig).getLoginDate());
			if(programAccess.getProgramAccessId()>0)
				getHibernateTemplate().update(programAccess);
			else
				getHibernateTemplate().save(programAccess);
		}
		
		logger.debug("**** Leaving from createOrUpdateOfficerAccess method ****");
		return true;
	}
	
	public Collection<ProgramAccessDTO> getAllProgramsWithAccessByGroupId(int userGroupId){
		logger.info("*** Entered to getAllProgramsWithAccessByOfficerId method ***");
		Map<Integer, ProgramAccessDTO> map = new TreeMap<Integer, ProgramAccessDTO>();
		
		List<SystemProgram> systemProgramList = null;
		Criteria sysCriteria = getSession().createCriteria(SystemProgram.class);
		sysCriteria.addOrder(Order.asc("systemProgramId"));
		systemProgramList    = sysCriteria.list();
		
		List<ProgramAccess> programAccessList = null;
		Criteria offCriteria = getSession().createCriteria(ProgramAccess.class);
		offCriteria.add(Restrictions.eq("userGroupId", userGroupId));
		//offCriteria.add(Restrictions.ne("access", "0"));
		offCriteria.setFetchMode("systemProgram", FetchMode.JOIN);
		programAccessList = offCriteria.list();
				
		for(SystemProgram systemProgram:systemProgramList){
			ProgramAccessDTO programAccessDTO = new ProgramAccessDTO();
			programAccessDTO.setSystemProgramId(systemProgram.getSystemProgramId());
			programAccessDTO.setParentId(systemProgram.getParentId());
			programAccessDTO.setDescription(systemProgram.getDescription());
			programAccessDTO.setUrl(systemProgram.getUrl());	
			programAccessDTO.setDefAccess(systemProgram.getAccess());
			map.put(systemProgram.getSystemProgramId(),programAccessDTO );
		}
		
		getHibernateTemplate().evict(systemProgramList);
		
		for(ProgramAccess officerAccess:programAccessList){
			ProgramAccessDTO accessDTO = map.get(officerAccess.getSystemProgram().getSystemProgramId());
			accessDTO.setAccess(officerAccess.getAccess());
			accessDTO.setProgramAccessId(officerAccess.getProgramAccessId());
			accessDTO.setVersion(officerAccess.getVersion());
			map.put(officerAccess.getSystemProgram().getSystemProgramId(), accessDTO);
		}
		
		logger.info("*** Leaving from getAllProgramsWithAccessByOfficerId method ***");
		return map.values();
	}
	
//	public UserConfig validateUser(int companyId,String userName,String password,String sessionId){
//			Criteria criteria = getSession().createCriteria(Officer.class);
//			criteria.add(Restrictions.eq("companyId",companyId));
//			criteria.add(Restrictions.eq("userName",userName));
//			criteria.add(Restrictions.eq("password",password));		
//			criteria.setFetchMode("pawner", FetchMode.JOIN);
//			criteria.setFetchMode("branch", FetchMode.JOIN);
//			criteria.setFetchMode("userGroup", FetchMode.JOIN);
//			criteria.setFetchMode("branch.systemDate", FetchMode.JOIN);
//			criteria.setFetchMode("accessBranchCollection", FetchMode.JOIN);
//			
//			Officer officer =null;
//			try{			
//			officer = (Officer)criteria.uniqueResult();
//			}catch(Exception e){
//				System.out.println(e.getStackTrace());
//			}
//			if(officer==null){
//				return null;
//			}
//			
//			if(officer.getIsActive()==0){
//				throw new CommonDataAccessException("error.user.inactive");
//			}
////			if(officer.getBranch().getSystemDate().getCurrentDate().compareTo(officer.getPasswordValidPeriod()) > 0){
////				throw new CommonDataAccessException("error.passexpired");
////			}
////			if(officer.getBranch().getSystemDate().getCurrentDate().compareTo(officer.getPasswordValidPeriod()) > 0){
////				long dateDiff = officer.getBranch().getSystemDate().getCurrentDate().getTime() - officer.getPasswordValidPeriod().getTime();
////				int days = (int) dateDiff / 1000 * 60 *60 * 24;
//////				if(days<=10)
//////					throw new CommonDataAccessException("error.passexpired");
////			}
//			
//			Company company = (Company)getSession().get(Company.class,companyId);	
//			Pawner pawner= (Pawner) officer.getPawner();
//			Calendar cal=Calendar.getInstance();
//					
//			UserConfig userConfig = new UserConfig();
//			userConfig.setUserId(officer.getOfficerId());
//			userConfig.setPawnerId(officer.getPawner().getPawnerId());
//			userConfig.setBranchId(officer.getBranch().getBranchId());
//			userConfig.setCompanyId(companyId);
//			userConfig.setUserGroupId(officer.getUserGroup().getUserGroupId());
//			userConfig.setLoginDate(officer.getBranch().getSystemDate().getCurrentDate());
//			userConfig.setAuthorizeMode(company.getAuthorizeMode());
//			userConfig.setLoginName(officer.getUserName());
//			userConfig.setBranchName(officer.getBranch().getBarnchName());
//			userConfig.setUserLogId(sessionId);
//			userConfig.setBrachEmail(officer.getBranch().getEmail());
//			userConfig.setUserEmail(pawner.getEmailAddress());
//			userConfig.setAccessBranchList((List<AccessBranch>)officer.getAccessBranchCollection());
//			userConfig.setIsActive(officer.getIsActive());
//			userConfig.setPasswordExpirePeriod(officer.getPasswordValidPeriod());
//			
//			UserLog userLog = new UserLog();
//			userLog.setBranchId(officer.getBranch().getBranchId());
//			userLog.setCompanyId(companyId);
//			userLog.setStatus(UserStatusEnum.LOGEDIN.getCode());
//			userLog.setTransactionId(userConfig.getUserLogId());
//			userLog.setUserId(officer.getPawner().getPawnerId());
//			getHibernateTemplate().save(userLog);
//			
//			return userConfig;
//	}

	public UserConfig validateUser(int companyId,String userName,String password,String sessionId){
		Criteria criteria = getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("companyId",companyId));
		criteria.add(Restrictions.eq("userName",userName));
		criteria.add(Restrictions.eq("password",password));		
		criteria.setFetchMode("pawner", FetchMode.JOIN);
		criteria.setFetchMode("branch", FetchMode.JOIN);
		criteria.setFetchMode("userGroup", FetchMode.JOIN);
		criteria.setFetchMode("branch.systemDate", FetchMode.JOIN);
		criteria.setFetchMode("accessBranchCollection", FetchMode.JOIN);
		
		Officer officer =null;
		try{			
		officer = (Officer)criteria.uniqueResult();
		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
		if(officer==null){
			return null;
		}
		
		Company company = (Company)getSession().get(Company.class,companyId);	
		Pawner pawner= (Pawner) officer.getPawner();
		Calendar cal=Calendar.getInstance();
				
		UserConfig userConfig = new UserConfig();
		userConfig.setUserId(officer.getOfficerId());
		userConfig.setPawnerId(officer.getPawner().getPawnerId());
		userConfig.setBranchId(officer.getBranch().getBranchId());
		userConfig.setCompanyId(companyId);
		userConfig.setUserGroupId(officer.getUserGroup().getUserGroupId());
		userConfig.setLoginDate(officer.getBranch().getSystemDate().getCurrentDate());
		userConfig.setAuthorizeMode(company.getAuthorizeMode());
		userConfig.setLoginName(officer.getUserName());
		userConfig.setBranchName(officer.getBranch().getBarnchName());
		userConfig.setUserLogId(sessionId);
		userConfig.setBrachEmail(officer.getBranch().getEmail());
		userConfig.setUserEmail(pawner.getEmailAddress());
		userConfig.setAccessBranchList((List<AccessBranch>)officer.getAccessBranchCollection());
		userConfig.setIsActive(officer.getIsActive());
		userConfig.setPasswordExpired(officer.getPasswordValidPeriod().compareTo(userConfig.getLoginDate()) < 0);
		
		UserLog userLog = new UserLog();
		userLog.setBranchId(officer.getBranch().getBranchId());
		userLog.setCompanyId(companyId);
		userLog.setStatus(UserStatusEnum.LOGEDIN.getCode());
		userLog.setTransactionId(userConfig.getUserLogId());
		userLog.setUserId(officer.getPawner().getPawnerId());
		getHibernateTemplate().save(userLog);
		
		return userConfig;
}
	public void logOut(UserConfig userConfig) {
		UserLog userLog=new UserLog();
		userLog.setBranchId(userConfig.getBranchId());
		userLog.setCompanyId(userConfig.getCompanyId());
		userLog.setStatus(UserStatusEnum.LOGEDOUT.getCode());
		userLog.setTransactionId(userConfig.getUserLogId());
		userLog.setUserId(userConfig.getUserId());
		getHibernateTemplate().save(userLog);		
	}

	public Boolean isActiveUser(int companyId,String userName){
		Criteria criteria = getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("companyId",companyId));
		criteria.add(Restrictions.eq("userName",userName));
		
		Officer officer=(Officer)criteria.uniqueResult();
		return officer.getIsActive()==1;
		
	}

	@Override
	public Collection<ProgramAccessDTO> getPasswordChange(int userGroupId) {
		logger.info("*** Entered to getPasswordChange method ***");
		Map<Integer, ProgramAccessDTO> map = new TreeMap<Integer, ProgramAccessDTO>();
		
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("systemProgramId", 10005));
		or.add(Restrictions.eq("systemProgramId", 45));
		or.add(Restrictions.eq("systemProgramId", 0));
		
		List<SystemProgram> systemProgramList = null;
		Criteria sysCriteria = getSession().createCriteria(SystemProgram.class);
		sysCriteria.add(or);
		systemProgramList    = sysCriteria.list();
		
		or=null;
		or=Restrictions.disjunction();
		or.add(Restrictions.eq("systemProgram.systemProgramId", 10005));
		or.add(Restrictions.eq("systemProgram.systemProgramId", 45));
		or.add(Restrictions.eq("systemProgram.systemProgramId", 0));
		
		List<ProgramAccess> programAccessList = null;
		Criteria offCriteria = getSession().createCriteria(ProgramAccess.class);
		offCriteria.add(Restrictions.eq("userGroupId", userGroupId));
		offCriteria.add(or);
		
		offCriteria.setFetchMode("systemProgram", FetchMode.JOIN);
		programAccessList = offCriteria.list();
				
		for(SystemProgram systemProgram:systemProgramList){
			ProgramAccessDTO programAccessDTO = new ProgramAccessDTO();
			programAccessDTO.setSystemProgramId(systemProgram.getSystemProgramId());
			programAccessDTO.setParentId(systemProgram.getParentId());
			programAccessDTO.setDescription(systemProgram.getDescription());
			programAccessDTO.setUrl(systemProgram.getUrl());	
			programAccessDTO.setDefAccess(systemProgram.getAccess());
			map.put(systemProgram.getSystemProgramId(),programAccessDTO );
		}
		
		getHibernateTemplate().evict(systemProgramList);
		
		for(ProgramAccess officerAccess:programAccessList){
			ProgramAccessDTO accessDTO = map.get(officerAccess.getSystemProgram().getSystemProgramId());
			accessDTO.setAccess(officerAccess.getAccess());
			accessDTO.setProgramAccessId(officerAccess.getProgramAccessId());
			accessDTO.setVersion(officerAccess.getVersion());
			map.put(officerAccess.getSystemProgram().getSystemProgramId(), accessDTO);
		}
		
		logger.info("*** Leaving from getPasswordChange method ***");
		return map.values();
	}
	@Override
	public List<UserLog> getAllTransactionsForUser(UserLog userLog) {
		logger.debug("**** Enter in to getBlockByProject method ****");
		List<UserLog> userLogList = null;
		Criteria criteria = getSession().createCriteria(UserLog.class);
		criteria.add(Restrictions.eq("userId", userLog.getUserLogId()));
		userLogList = criteria.list();

		logger.debug("**** Leaving from getBlockByProject method ****");
		return userLogList;
	}
	
}
