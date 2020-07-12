package com.m4.pawning.service.impl;

import java.util.Calendar;
import java.util.List;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.PasswordService;
import com.m4.core.util.PasswordValidator;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.OfficerDAO;
import com.m4.pawning.dao.ParameterValueDAO;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.OfficerHistory;
import com.m4.pawning.domain.ParameterValue;
import com.m4.pawning.service.OfficerService;
import com.m4.pawning.util.ParameterEnum;

public class OfficerServiceImpl extends AuthorizableServiceImpl implements OfficerService {
	private OfficerDAO officerDAO;
	private ParameterValueDAO parameterValueDAO;
	private static final PasswordValidator PASSWORD_VALIDATOR= new PasswordValidator();
	String returnValue = null; 
	List<OfficerHistory> officerHistories = null;
	public void setOfficerDAO(OfficerDAO officerDAO) {
		this.officerDAO = officerDAO;
	}
		public void setParameterValueDAO(ParameterValueDAO parameterValueDAO) {
		this.parameterValueDAO = parameterValueDAO;
	}

	public void createOfficer(UserConfig userConfig, Officer officer)throws PawnException {
		returnValue=PASSWORD_VALIDATOR.passwordValidation(officer.getUserName(), officer.getPassword());
        
        if ( returnValue!=null){
			throw new CommonDataAccessException(returnValue);
		}
        try {
			officer.setPassword(PasswordService.getInstance().encrypt(officer.getPassword()));
		} catch (Exception e) {
			throw new CommonDataAccessException("unknown");
		}
		officerDAO.createOfficer(userConfig, officer);
	}

	public void removeOfficer(UserConfig userConfig, Officer officer)throws PawnException {
		officerDAO.removeOfficer(userConfig, officer);
	}

	public void updateOfficer(UserConfig userConfig, Officer officer)throws PawnException {
		officerDAO.updateOfficer(userConfig, officer);
	}

	public DataBag getAllOfficer(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return officerDAO.getAllOfficer(userConfig, criteriaList);
	}

	public Officer getOfficerById(UserConfig userConfig, int recordId)throws PawnException {
		return officerDAO.getOfficerById(userConfig, recordId);
	}

	public Officer getOfficerByUserName(UserConfig userConfig, String code)throws PawnException {
		return officerDAO.getOfficerByUserName(userConfig, code);
	}
	public Officer getOfficerByPawnerId(UserConfig userConfig,int pawnerId)throws PawnException{
		return officerDAO.getOfficerByPawnerId(userConfig, pawnerId);
	}

	@Override
	public void resetPassword(UserConfig userConfig, Officer officer,String newPassword)	throws PawnException {
		
//		if(officer.getPassword().equals(newPassword)) {
//			throw new PawnException("errors.oldequlnew");
//		}
		returnValue=PASSWORD_VALIDATOR.passwordValidation(officer.getUserName(), newPassword);
        
        if ( returnValue!=null){
			throw new PawnException(returnValue);
		}
        
        try {
			officer.setPassword(PasswordService.getInstance().encrypt(newPassword));
			officerHistories=officerDAO.validatePreviousPassword(officer);
		} catch (Exception e) {
			throw new PawnException("errors.recordnotfound");
		}
			
		for (OfficerHistory officerHistory : officerHistories) {
			if(officerHistory.getPassword().equals(officer.getPassword())){
				throw new PawnException("error.passhistthree");	
			}
		}			
		officerDAO.resetPassword(userConfig, officer);
	}


	@Override
	public void changePassword(UserConfig userConfig, Officer officer)
			throws PawnException {
		officerDAO.changePassword(userConfig, officer);
		
	}
}
